package com.example.maintenancebuddy.data;

import com.example.maintenancebuddy.data.dao.UserProfileDao;
import com.example.maintenancebuddy.data.model.UserProfile;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
@Singleton
public class UserRepository {

    public enum AuthState {
        NOT_AUTHENTICATED, AUTHENTICATING, AUTHENTICATED;
    }

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private final FirebaseAuth      firebaseAuth;

    private final UserProfileDao userProfileDao;

    private volatile BehaviorSubject<UserProfile> currentUser;
    private          BehaviorSubject<AuthState> authState;

    @Inject
    public UserRepository(FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestore, UserProfileDao userProfileDao) {
        currentUser = BehaviorSubject.create();
        this.firebaseAuth = firebaseAuth;
        this.userProfileDao = userProfileDao;
        this.authState = BehaviorSubject.createDefault(AuthState.NOT_AUTHENTICATED);
    }

    public UserProfile getCurrentUser() {
        UserProfile currentUserProfile = currentUser.getValue();
        if(currentUserProfile == null) return null;
        return new UserProfile(currentUserProfile); // copy user profile to avoid someone messing with it
    }

    public AuthState getAuthState() {
        return authState.getValue();
    }

    public Observable<AuthState> getAuthStateObservable() {
        return authState.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    public boolean isAuthenticated() { return getAuthState() == AuthState.AUTHENTICATED; }

    /**
     * Use RxJava stream to carry out the multistep login process. One nice feature of RxJava
     * is that errors propagate through the stream so only a single error handler is needed at the very end.
     * 1) Authenticate with Firebase Authentication
     * 2) On success, continue the stream by fetching the authenticated user's profile (.flatMap())
     * 3) Update the authentication status depending on if everything succeeded or failed
     * 4) Return a Completable which lets the caller know whether the login completed or failed with an exception
     * @param email
     * @param password
     * @return
     */
    public Completable login(String email, String password) {
        if(getAuthState() == AuthState.AUTHENTICATED) {
            return Completable.error(new IllegalStateException("Already authenticated, must logout before login"));
        }
        Single<FirebaseUser> userAuthCompletable = Single.create(emitter -> {
            Task<AuthResult> authTask = firebaseAuth.signInWithEmailAndPassword(email, password);
            try {
                AuthResult authResult = Tasks.await(authTask);
                if(authResult != null && authResult.getUser() != null) {
                    if(!emitter.isDisposed()) emitter.onSuccess(authResult.getUser());
                } else {
                    if(!emitter.isDisposed()) emitter.onError(new IllegalStateException("User authentication status invalid"));
                }
            } catch(Exception e) {
                if(!emitter.isDisposed()) emitter.onError(e);
            }
        });
        Single<UserProfile> userProfileSingle = userAuthCompletable
                .flatMap((Function<FirebaseUser, SingleSource<UserProfile>>) firebaseUser -> userProfileDao.getWithUID(firebaseUser.getUid()))
                .doOnSubscribe(disposable -> authState.onNext(AuthState.AUTHENTICATING))
                .doOnSuccess(userProfile -> {
                    authState.onNext(AuthState.AUTHENTICATED);
                    currentUser.onNext(userProfile);
                })
                .doOnError(throwable -> authState.onNext(AuthState.NOT_AUTHENTICATED));
        return Completable.fromSingle(userProfileSingle);
    }

    public Completable createUser(String firstName, String lastName, String emailAddress, String password) {
        Single<FirebaseUser> userAuthCompletable = Single.create(emitter -> {
            Task<AuthResult> authTask = firebaseAuth.createUserWithEmailAndPassword(emailAddress, password);
            try {
                AuthResult authResult = Tasks.await(authTask);
                if(authResult != null && authResult.getUser() != null) {
                   if(!emitter.isDisposed()) emitter.onSuccess(authResult.getUser());
                } else {
                    if(!emitter.isDisposed()) emitter.onError(new IllegalStateException("User authentication result invalid"));
                }
            } catch(Exception e) {
                if(!emitter.isDisposed()) emitter.onError(e);
            }
        });
        return userAuthCompletable
                .map(firebaseUser -> new UserProfile(firebaseUser.getUid(), firstName, lastName))
                .flatMapCompletable(userProfileDao::create)
                .doOnError(throwable -> {
                    // delete authenticated user if creating their user profile in the database fails
                });
    }
}