package com.juanmcardenas.auth;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.juanmcardenas.auth.db.AuthDatabase;
import com.juanmcardenas.auth.db.dao.AttemptsDao;
import com.juanmcardenas.auth.db.dao.UsersDao;
import com.juanmcardenas.auth.db.models.Attempt;
import com.juanmcardenas.auth.db.models.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DbTest {

    private AttemptsDao attemptsDao;
    private UsersDao usersDao;
    private AuthDatabase db;
    private CompositeDisposable disposable;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AuthDatabase.class).build();
        usersDao = db.getUsersDao();
        attemptsDao = db.getAttemptsDao();
        disposable = new CompositeDisposable();
    }

    @Test
    public void testUserInsertion() {
        // Create a test user
        User testUser =  new User(0, "martin", "Pass.123");


        usersDao.insertUser(testUser).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                usersDao.getUserByUsername("martin").subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(User user) {
                        Assert.assertEquals("martin", user.getUsername());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Assert.fail();
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                Assert.fail(e.getMessage());
            }
        });


    }

    @Test
    public void testIncorrectUserLogin() {
        // Create a test user
        User testUser =  new User(0, "martin", "Pass.123");
        // Insert into DB and then read another username and verify that the result is empty
        usersDao.insertUser(testUser).andThen(usersDao.getUserByUsername("anotherMartin"))
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(User user) {
                        Assert.assertNull(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Pass
                        Assert.assertTrue(true);
                    }
                });
    }

    @Test
    public void testAttemptsLogs() {
        // Login 1 fail and 1 success attempt
        Attempt successAttempt = new Attempt(new Date().getTime(), "SUCCESS");
        Attempt failAttempt = new Attempt(new Date().getTime(), "FAIL");
        // Insert into db
        attemptsDao.insertAttempt(successAttempt).subscribe();
        attemptsDao.insertAttempt(failAttempt).subscribe();

        // Then read the DB to verify the logs
        attemptsDao.getAttempts().subscribe(new SingleObserver<List<Attempt>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Attempt> attempts) {
                Assert.assertNotNull(attempts);
                Assert.assertEquals(2, attempts.size());
                Assert.assertEquals("SUCCESS", attempts.get(0).getResult());
                Assert.assertEquals("FAIL", attempts.get(1).getResult());
            }

            @Override
            public void onError(Throwable e) {
                Assert.fail();
            }
        });
    }


}
