package com.example.maintenancebuddy.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.maintenancebuddy.data.dao.UserProfileDao;
import com.example.maintenancebuddy.data.model.UserProfile;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;

class UserRepositoryTest {

    private UserRepository out;

    @Mock
    private FirebaseAuth firebaseAuthMock;

    @Mock
    private FirebaseFirestore firebaseFirestoreMock;

    @Mock
    private UserProfileDao userProfileDaoMock;

    @Mock
    private AuthResult authResultMock;

    @Mock
    private FirebaseUser firebaseUserMock;

    @Mock
    private Task<AuthResult> authResultTaskMock;

    private MockedStatic<Tasks> tasksMock;

    @BeforeEach
    void setUp() {
        firebaseAuthMock = mock(FirebaseAuth.class);
        firebaseFirestoreMock = mock(FirebaseFirestore.class);
        userProfileDaoMock = mock(UserProfileDao.class);
        authResultMock = mock(AuthResult.class);
        firebaseUserMock = mock(FirebaseUser.class);
        authResultTaskMock = mock(Task.class);
        tasksMock = mockStatic(Tasks.class);
        out = new UserRepository(firebaseAuthMock, firebaseFirestoreMock, userProfileDaoMock);
    }

    @AfterEach
    void tearDown() {
        out = null;
        tasksMock.close(); // static mocks must be closed after each test
    }

    @Test
    public void testNotAuthenticatedUponConstruction() {
        assertEquals(UserRepository.AuthState.NOT_AUTHENTICATED, out.getAuthState());
    }

    @Test
    public void testFirebaseLoginCalledOnLogin() {
        when(userProfileDaoMock.create(any())).thenReturn(Completable.error(new RuntimeException()));
        when(firebaseAuthMock.signInWithEmailAndPassword("success@test.com", "correct_password")).thenReturn(authResultTaskMock);

        out.login("success@test.com", "correct_password").test(true);

        verify(firebaseAuthMock, times(1)).signInWithEmailAndPassword("success@test.com", "correct_password");
    }

    @Test
    public void testCompletionAfterSuccessfulLogin() throws ExecutionException, InterruptedException {
        tasksMock.when(Tasks.await(any())).thenReturn(authResultMock); // blocks Android main thread, which doesn't exist in the JUnit environment so it must be mocked

        UserProfile userProfileFake = new UserProfile("uid", "first", "last");
        when(userProfileDaoMock.getWithUID(any())).thenReturn(Single.just(userProfileFake));
        when(authResultTaskMock.isSuccessful()).thenReturn(true);
        when(authResultMock.getUser()).thenReturn(firebaseUserMock);
        when(firebaseAuthMock.signInWithEmailAndPassword("success@test.com", "correct_password")).thenReturn(authResultTaskMock);

        TestObserver<Void> testObserver = TestObserver.create();
        out.login("success@test.com", "correct_password").subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void testNotCompleteAfterFailedLogin() throws ExecutionException, InterruptedException {
        tasksMock.when(Tasks.await(any())).thenReturn(authResultMock); // blocks Android main thread, which doesn't exist in the JUnit environment so it must be mocked

        when(authResultTaskMock.isSuccessful()).thenReturn(false);
        when(firebaseAuthMock.signInWithEmailAndPassword(any(), any())).thenReturn(authResultTaskMock);

        TestObserver<Void> testObserver = TestObserver.create();
        out.login("success@test.com", "wrong_password").subscribe(testObserver);

        testObserver.assertNotComplete();
        testObserver.assertError(Exception.class);
    }

    @Test
    public void testAuthenticatedAfterSuccessfulLogin() throws ExecutionException, InterruptedException {
        tasksMock.when(Tasks.await(any())).thenReturn(authResultMock); // blocks Android main thread, which doesn't exist in the JUnit environment so it must be mocked

        UserProfile userProfileFake = new UserProfile("uid", "first", "last");
        when(userProfileDaoMock.getWithUID(any())).thenReturn(Single.just(userProfileFake));
        when(authResultTaskMock.isSuccessful()).thenReturn(true);
        when(authResultMock.getUser()).thenReturn(firebaseUserMock);
        when(firebaseAuthMock.signInWithEmailAndPassword(any(), any())).thenReturn(authResultTaskMock);

        TestObserver<Void> testObserver = TestObserver.create();
        out.login("success@test.com", "correct_password").subscribe(testObserver);

        assertEquals(UserRepository.AuthState.AUTHENTICATED, out.getAuthState());
    }

    @Test
    public void testNotAuthenticatedAfterFailedLogin() throws ExecutionException, InterruptedException {
        tasksMock.when(Tasks.await(any())).thenReturn(authResultMock); // blocks Android main thread, which doesn't exist in the JUnit environment so it must be mocked

        when(authResultTaskMock.isSuccessful()).thenReturn(false);
        when(firebaseAuthMock.signInWithEmailAndPassword(any(), any())).thenReturn(authResultTaskMock);

        TestObserver<Void> testObserver = TestObserver.create();
        out.login("success@test.com", "wrong_password").subscribe(testObserver);

        assertEquals(UserRepository.AuthState.NOT_AUTHENTICATED, out.getAuthState());
    }
}