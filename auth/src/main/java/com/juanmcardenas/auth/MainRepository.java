package com.juanmcardenas.auth;

import androidx.lifecycle.MutableLiveData;

import com.juanmcardenas.auth.db.models.Attempt;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public interface MainRepository {


    void login(String username, String password, String securityQuestion, String answer);

    void register(String username, String password, String securityQuestion, String answer);

    void getAttemptList(MutableLiveData<Attempt> attemptMutableLiveData);

}
