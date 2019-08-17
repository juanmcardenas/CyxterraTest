package com.juanmcardenas.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.juanmcardenas.auth.db.AuthDatabase;
import com.juanmcardenas.auth.db.models.Attempt;
import com.juanmcardenas.auth.db.models.User;
import com.juanmcardenas.auth.network.DateRequest;
import com.juanmcardenas.auth.util.SecurityUtil;

import java.util.Date;
import java.util.List;

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
    public static final int RESULT_ERROR_DB_ERROR = 7;


    public void login(AppCompatActivity activity, String username, String password, String securityQuestion, String answer, MutableLiveData<Integer> resultCodeLiveData) {
        // Null check
        if (resultCodeLiveData == null) {
            return;
        }
        MutableLiveData<Date> liveData = new MutableLiveData<>();
        // First get the date
        new DateRequest().get(activity, liveData);
        liveData.observe(activity, date -> {
            if (date == null) {
                resultCodeLiveData.postValue(RESULT_ERROR_NETWORK_ERROR);
                registerAttempt(activity, new Date().getTime(), false);
                return;
            }
            // Now that we got a date, then check all the parameters
            // Check username validity
            if (!SecurityUtil.isEmailAddressValid(username)) {
                resultCodeLiveData.postValue(RESULT_ERROR_INVALID_USERNAME);
                registerAttempt(activity, date.getTime(), false);
            }
            // Check password validity
            if (!SecurityUtil.isPasswordValid(password)) {
                resultCodeLiveData.postValue(RESULT_ERROR_INVALID_PASSWORD);
                registerAttempt(activity, date.getTime(), false);
            }
            // Check security question validity
            if (!SecurityUtil.isSecurityQuestionValid(securityQuestion)) {
                resultCodeLiveData.postValue(RESULT_ERROR_INVALID_QUESTION);
                registerAttempt(activity, date.getTime(), false);
            }
            // Check security answer validity
            if (!SecurityUtil.isSecurityAnswerValid(answer)) {
                resultCodeLiveData.postValue(RESULT_ERROR_INVALID_ANSWER);
                registerAttempt(activity, date.getTime(), false);
            }
            // Encode password
            // TODO
            // Verify that user is on db
            AuthDatabase.getInstance(activity).getUsersDao().getUserByUsername(username).observe(activity, user1 -> {
                if (user1 != null && user1.getUsername() != null && user1.getUsername().contentEquals(username)
                && user1.getPass().contentEquals(password)) {
                    // register SUCCESS attempt
                    registerAttempt(activity, date.getTime(), true);
                    // send response back
                    resultCodeLiveData.postValue(RESULT_SUCCESS);
                } else {
                    // register FAIL attempt
                    registerAttempt(activity, date.getTime(), false);
                    // send response back
                    resultCodeLiveData.postValue(RESULT_ERROR_DB_ERROR);
                }
            });
        });
    }

    public void register(AppCompatActivity activity, String username, String password, String securityQuestion, String answer, MutableLiveData<Integer> resultCodeLiveData) {
        // Null check
        if (resultCodeLiveData == null) {
            return;
        }
        MutableLiveData<Date> liveData = new MutableLiveData<>();
        // First get the date
        new DateRequest().get(activity, liveData);
        liveData.observe(activity, date -> {
            if (date == null) {
                resultCodeLiveData.postValue(RESULT_ERROR_NETWORK_ERROR);
                registerAttempt(activity, new Date().getTime(), false);
                return;
            }
            // Now that we got a date, then check all the parameters
            // Check username validity
            if (!SecurityUtil.isEmailAddressValid(username)) {
                resultCodeLiveData.postValue(RESULT_ERROR_INVALID_USERNAME);
                registerAttempt(activity, date.getTime(), false);
            }
            // Check password validity
            if (!SecurityUtil.isPasswordValid(password)) {
                resultCodeLiveData.postValue(RESULT_ERROR_INVALID_PASSWORD);
                registerAttempt(activity, date.getTime(), false);
            }
            // Check security question validity
            if (!SecurityUtil.isSecurityQuestionValid(securityQuestion)) {
                resultCodeLiveData.postValue(RESULT_ERROR_INVALID_QUESTION);
                registerAttempt(activity, date.getTime(), false);
            }
            // Check security answer validity
            if (!SecurityUtil.isSecurityAnswerValid(answer)) {
                resultCodeLiveData.postValue(RESULT_ERROR_INVALID_ANSWER);
                registerAttempt(activity, date.getTime(), false);
            }
            // Encode password
            // TODO
            // Perform registration
            User user = new User(username, password);
            AuthDatabase.getInstance(activity).getUsersDao().insertUser(user);
            // Verify that user is on db
            AuthDatabase.getInstance(activity).getUsersDao().getUserByUsername(username).observe(activity, user1 -> {
                if (user1 != null && user1.getUsername() != null && user1.getUsername().contentEquals(username)) {
                    // register SUCCESS attempt
                    registerAttempt(activity, date.getTime(), true);
                    // send response back
                    resultCodeLiveData.postValue(RESULT_SUCCESS);
                } else {
                    // register FAIL attempt
                    registerAttempt(activity, date.getTime(), false);
                    // send response back
                    resultCodeLiveData.postValue(RESULT_ERROR_DB_ERROR);
                }
            });
        });
    }

    public void getAttemptList(AppCompatActivity activity, MutableLiveData<List<Attempt>> attemptMutableLiveData) {
        AuthDatabase.getInstance(activity).getAttemptsDao().getAttempts().observe(activity, attempts -> {
            if (attemptMutableLiveData != null) {
                attemptMutableLiveData.postValue(attempts);
            }
        });
    }

    private void registerAttempt(AppCompatActivity activity, long date, boolean isSuccess) {
        AuthDatabase.getInstance(activity).getAttemptsDao().insertAttempt(new Attempt(date, isSuccess ? "SUCCESS" : "FAIL"));
    }


}
