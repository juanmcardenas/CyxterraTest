package com.juanmcardenas.cyxandtest.di.components;

import com.juanmcardenas.auth.AuthManager;
import com.juanmcardenas.cyxandtest.di.modules.AppModule;
import com.juanmcardenas.cyxandtest.ui.attempts.AttemptsFragment;
import com.juanmcardenas.cyxandtest.ui.signin.SignInFragment;
import com.juanmcardenas.cyxandtest.ui.signup.SignUpFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Martin Cardenas on 2019-08-19.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    AuthManager providesAuthManager();

   void inject(SignInFragment fragment);
   void inject(SignUpFragment fragment);
   void inject(AttemptsFragment fragment);

}
