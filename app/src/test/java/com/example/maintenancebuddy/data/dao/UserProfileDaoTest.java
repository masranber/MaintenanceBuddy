package com.example.maintenancebuddy.data.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.maintenancebuddy.data.UserRepository;
import com.example.maintenancebuddy.data.model.UserProfile;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;

class UserProfileDaoTest {

    UserProfileDao sut;

    @Mock
    private FirebaseFirestore firebaseFirestoreMock;

    @Mock
    private AuthResult authResultMock;

    @Mock
    private FirebaseUser firebaseUserMock;

    @Mock
    private Task<AuthResult> authResultTaskMock;

    private MockedStatic<Tasks> tasksMock;

    @BeforeEach
    void setUp() {
        firebaseFirestoreMock = mock(FirebaseFirestore.class);
        authResultMock = mock(AuthResult.class);
        firebaseUserMock = mock(FirebaseUser.class);
        authResultTaskMock = mock(Task.class);
        tasksMock = mockStatic(Tasks.class);
        sut = new UserProfileDao(firebaseFirestoreMock);
    }

    @AfterEach
    void tearDown() {
        sut = null;
        tasksMock.close(); // static mocks must be closed after each test
    }

    @Test
    void testCreateTaskSendsUserProfile() throws ExecutionException, InterruptedException {
        tasksMock.when(Tasks.await(any())).thenReturn(authResultMock); // blocks Android main thread, which doesn't exist in the JUnit environment so it must be mocked

        UserProfile userProfileFake = new UserProfile("uid", "first", "last");

        CollectionReference cRefMock = mock(CollectionReference.class);
        DocumentReference dRefMock = mock(DocumentReference.class);
        Task<Void> voidTaskMock = mock(Task.class);

        when(firebaseFirestoreMock.collection(any())).thenReturn(cRefMock);
        when(cRefMock.document(any())).thenReturn(dRefMock);
        when(dRefMock.set(userProfileFake)).thenReturn(voidTaskMock);
        when(voidTaskMock.isSuccessful()).thenReturn(true);

        TestObserver<Void> testObserver = TestObserver.create();
        sut.create(userProfileFake).subscribe(testObserver);

        verify(dRefMock, times(1)).set(any());
    }

    @Test
    void testCreateTaskSuccess() throws ExecutionException, InterruptedException {
        tasksMock.when(Tasks.await(any())).thenReturn(authResultMock); // blocks Android main thread, which doesn't exist in the JUnit environment so it must be mocked

        UserProfile userProfileFake = new UserProfile("uid", "first", "last");

        CollectionReference cRefMock = mock(CollectionReference.class);
        DocumentReference dRefMock = mock(DocumentReference.class);
        Task<Void> voidTaskMock = mock(Task.class);

        when(firebaseFirestoreMock.collection(any())).thenReturn(cRefMock);
        when(cRefMock.document(any())).thenReturn(dRefMock);
        when(dRefMock.set(userProfileFake)).thenReturn(voidTaskMock);
        when(voidTaskMock.isSuccessful()).thenReturn(true);

        TestObserver<Void> testObserver = TestObserver.create();
        sut.create(userProfileFake).subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    void testCreateTaskFailed() throws ExecutionException, InterruptedException {

        UserProfile userProfileFake = new UserProfile("uid", "first", "last");

        CollectionReference cRefMock = mock(CollectionReference.class);
        DocumentReference dRefMock = mock(DocumentReference.class);
        Task<Void> voidTaskMock = mock(Task.class);

        when(firebaseFirestoreMock.collection(any())).thenReturn(null); // shouldn't be null
        when(cRefMock.document(any())).thenReturn(dRefMock);
        when(dRefMock.set(userProfileFake)).thenReturn(null); // shouldn't be null
        when(voidTaskMock.isSuccessful()).thenReturn(false);

        tasksMock.when(Tasks.await(any())).thenReturn(null);

        TestObserver<Void> testObserver = TestObserver.create();
        sut.create(userProfileFake).subscribe(testObserver);

        // For some reason task is succeeding even when the mock is returning false for isSuccessful()...
        testObserver.assertError(Exception.class);
        testObserver.assertNotComplete();
    }

    @Test
    void testGetTaskSuccess() throws ExecutionException, InterruptedException {

        UserProfile userProfileFake = new UserProfile("uid", "first", "last");

        CollectionReference cRefMock = mock(CollectionReference.class);
        DocumentReference dRefMock = mock(DocumentReference.class);
        Task<DocumentSnapshot> docTaskMock = mock(Task.class);
        DocumentSnapshot dSnapMock = mock(DocumentSnapshot.class);

        when(firebaseFirestoreMock.collection(any())).thenReturn(cRefMock);
        when(cRefMock.document(any())).thenReturn(dRefMock);
        when(dRefMock.get()).thenReturn(docTaskMock);
        when(docTaskMock.getResult()).thenReturn(dSnapMock);
        when(docTaskMock.isSuccessful()).thenReturn(true);
        when(dSnapMock.toObject(UserProfile.class)).thenReturn(userProfileFake);

        tasksMock.when(Tasks.await(any())).thenReturn(dSnapMock);

        TestObserver<UserProfile> testObserver = TestObserver.create();
        sut.getWithUID(userProfileFake.uid).subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(userProfileFake);
    }

    @Test
    void testGetTaskFailedMissingUser() throws ExecutionException, InterruptedException {

        UserProfile userProfileFake = new UserProfile("uid", "first", "last");

        CollectionReference cRefMock = mock(CollectionReference.class);
        DocumentReference dRefMock = mock(DocumentReference.class);
        Task<DocumentSnapshot> docTaskMock = mock(Task.class);
        DocumentSnapshot dSnapMock = mock(DocumentSnapshot.class);

        when(firebaseFirestoreMock.collection(any())).thenReturn(cRefMock);
        when(cRefMock.document(any())).thenReturn(dRefMock);
        when(dRefMock.get()).thenReturn(docTaskMock);
        when(docTaskMock.getResult()).thenReturn(dSnapMock);
        when(docTaskMock.isSuccessful()).thenReturn(true);
        when(dSnapMock.toObject(UserProfile.class)).thenReturn(null); // returns null when document couldn't be found (user not found)

        tasksMock.when(Tasks.await(any())).thenReturn(dSnapMock);

        TestObserver<UserProfile> testObserver = TestObserver.create();
        sut.getWithUID(userProfileFake.uid).subscribe(testObserver);

        testObserver.assertError(Exception.class);
        testObserver.assertNotComplete();
    }
}