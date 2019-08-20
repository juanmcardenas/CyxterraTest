package com.juanmcardenas.cyxandtest.di.modules;

import android.content.Context;

import com.juanmcardenas.auth.AuthManager;
import com.juanmcardenas.cyxandtest.MainApplication;
import com.juanmcardenas.cyxandtest.model.repository.MainRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Martin Cardenas on 8/15/19.
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    Context providesContext(MainApplication application){
        return application;
    }

    @Singleton
    @Provides
    MainRepository providesRepository(MainRepository repository){
        return repository;
    }

    @Singleton
    @Provides
    AuthManager providesAuthManager(){
        return new AuthManager();
    }

}
