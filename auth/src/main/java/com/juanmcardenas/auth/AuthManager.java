package com.juanmcardenas.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.juanmcardenas.auth.db.AuthDatabase;
import com.juanmcardenas.auth.db.models.Attempt;
import com.juanmcardenas.auth.db.models.User;
import com.juanmcardenas.auth.network.DateRequest;
import com.juanmcardenas.auth.util.SecurityUtil;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public class AuthManager {

    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_ERROR_NO_INTERNET = 1;
    public static final int RESULT_ERROR_NO_GPS = 2;
    public static final int RESULT_ERROR_INVALID_USERNAME = 3;
    public static final int RESULT_ERROR_INVALID_PASSWORD = 4;
    public static final int RESULT_ERROR_INVALID_QUESTION = 5;
    public static final int RESULT_ERROR_INVALID_ANSWER = 6;
    public static final int RESULT_ERROR_NETWORK_ERROR = 7;
    public static final int RESULT_ERROR_DB_ERROR = 8;
    public static final int RESULT_ERROR_INVALID_CREDENTIALS = 9;


    public Single<User> login(FragmentActivity activity, String username, String password, String securityQuestion, String answer) {
        return Single.create(emitter -> new DateRequest().get(activity).subscribe(new SingleObserver<Date>() {
            @Override
            public void onSubscribe(Disposable d) {
//                d.dispose();
            }

            @Override
            public void onSuccess(Date date) {
                if (date == null) {
                    emitter.onError(new Exception("" + RESULT_ERROR_NETWORK_ERROR));
                    registerAttempt(activity, new Date().getTime(), false);
                    return;
                }
                // Now that we got a date, then check all the parameters
                // Check username validity
                if (!SecurityUtil.isEmailAddressValid(username)) {
                    emitter.onError(new Exception("" + RESULT_ERROR_INVALID_USERNAME));
                    registerAttempt(activity, date.getTime(), false);
                    return;
                }
                // Check password validity
                if (!SecurityUtil.isPasswordValid(password)) {
                    emitter.onError(new Exception("" + RESULT_ERROR_INVALID_PASSWORD));
                    registerAttempt(activity, date.getTime(), false);
                    return;
                }
                // Check security question validity
                if (!SecurityUtil.isSecurityQuestionValid(securityQuestion)) {
                    emitter.onError(new Exception("" + RESULT_ERROR_INVALID_QUESTION));
                    registerAttempt(activity, date.getTime(), false);
                    return;
                }
                // Check security answer validity
                if (!SecurityUtil.isSecurityAnswerValid(answer)) {
                    emitter.onError(new Exception("" + RESULT_ERROR_INVALID_ANSWER));
                    registerAttempt(activity, date.getTime(), false);
                    return;
                }
                // Encode password
                // TODO
                // Verify that user is on db
                AuthDatabase.getAuthDatabase(activity).getUsersDao().getUserByUsername(username)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        d.dispose();
                    }

                    @Override
                    public void onSuccess(User user) {
                        // Null checks
                        if (user == null || user.getUsername() == null || user.getPass() == null) {
                            emitter.onError(new Exception("" + RESULT_ERROR_INVALID_CREDENTIALS));
                            registerAttempt(activity, date.getTime(), false);
                        } else if (user.getUsername().contentEquals(username)
                                && user.getPass().contentEquals(password)) {
                            // register SUCCESS attempt
                            registerAttempt(activity, date.getTime(), true);
                            // send response back
                            emitter.onSuccess(user);
                        } else {
                            // register FAIL attempt
                            registerAttempt(activity, date.getTime(), false);
                            // send response back
                            emitter.onError(new Exception("" + RESULT_ERROR_DB_ERROR));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // register FAIL attempt
                        registerAttempt(activity, date.getTime(), false);
                        // send response back
                        emitter.onError(new Exception("" + RESULT_ERROR_INVALID_CREDENTIALS));
                    }
                });

            }

            @Override
            public void onError(Throwable e) {
                // register FAIL attempt
                registerAttempt(activity, new Date().getTime(), false);
                // send response back
                emitter.onError(new Exception("" + RESULT_ERROR_DB_ERROR));
            }
        }));

    }

    public Single<User> register(AppCompatActivity activity, String username, String password, String securityQuestion, String answer) {
        return Single.create(emitter -> new DateRequest().get(activity).subscribe(new SingleObserver<Date>() {
            @Override
            public void onSubscribe(Disposable d) {
//                        d.dispose();
            }

            @Override
            public void onSuccess(Date date) {
                if (date == null) {
                    emitter.onError(new Exception(RESULT_ERROR_NETWORK_ERROR + ""));
                    registerAttempt(activity, new Date().getTime(), false);
                    return;
                }
                // Now that we got a date, then check all the parameters
                // Check username validity
                if (!SecurityUtil.isEmailAddressValid(username)) {
                    emitter.onError(new Exception(RESULT_ERROR_INVALID_USERNAME + ""));
                    registerAttempt(activity, date.getTime(), false);
                }
                // Check password validity
                if (!SecurityUtil.isPasswordValid(password)) {
                    emitter.onError(new Exception(RESULT_ERROR_INVALID_PASSWORD + ""));
                    registerAttempt(activity, date.getTime(), false);
                }
                // Check security question validity
                if (!SecurityUtil.isSecurityQuestionValid(securityQuestion)) {
                    emitter.onError(new Exception(RESULT_ERROR_INVALID_QUESTION + ""));
                    registerAttempt(activity, date.getTime(), false);
                }
                // Check security answer validity
                if (!SecurityUtil.isSecurityAnswerValid(answer)) {
                    emitter.onError(new Exception(RESULT_ERROR_INVALID_ANSWER + ""));
                    registerAttempt(activity, date.getTime(), false);
                }
                // Encode password
                // TODO
                // Perform registration
                User user = new User(0 ,username, password);
                AuthDatabase.getAuthDatabase(activity).getUsersDao().insertUser(user)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
                // Verify that user is on db
                AuthDatabase.getAuthDatabase(activity).getUsersDao().getUserByUsername(username)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        d.dispose();
                    }

                    @Override
                    public void onSuccess(User user) {
                        if (user != null && user.getUsername() != null && user.getUsername().contentEquals(username)) {
                            // register SUCCESS attempt
                            registerAttempt(activity, date.getTime(), true);
                            // send response back
                            emitter.onSuccess(user);
                        } else {
                            // register FAIL attempt
                            registerAttempt(activity, date.getTime(), false);
                            // send response back
                            emitter.onError(new Exception("" + RESULT_ERROR_INVALID_CREDENTIALS));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // register FAIL attempt
                        registerAttempt(activity, date.getTime(), false);
                        // send response back
                        emitter.onError(new Exception("" + RESULT_ERROR_INVALID_CREDENTIALS));
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                // register FAIL attempt
                registerAttempt(activity, new Date().getTime(), false);
                // send response back
                emitter.onError(new Exception("" + RESULT_ERROR_NO_INTERNET));

            }
        }));
    }

    public Single<List<Attempt>> getAttemptList(FragmentActivity activity) {
        return Single.create(emitter -> AuthDatabase.getAuthDatabase(activity).getAttemptsDao().getAttempts()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Attempt>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Attempt> attempts) {
                if (attempts != null) {
                    emitter.onSuccess(attempts);
                } else {
                    emitter.onError(new Exception(RESULT_ERROR_DB_ERROR + ""));
                }
            }

            @Override
            public void onError(Throwable e) {
                emitter.onError(new Exception(RESULT_ERROR_DB_ERROR + ""));
            }
        }));
    }

    private void registerAttempt(FragmentActivity activity, long date, boolean isSuccess) {
        AuthDatabase.getAuthDatabase(activity).getAttemptsDao()
                .insertAttempt(new Attempt(date, isSuccess ? "SUCCESS" : "FAIL"))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
