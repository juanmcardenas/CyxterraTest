package com.juanmcardenas.cyxandtest;

import android.app.Application;


/**
 * Created by Martin Cardenas on 2019-08-19.
 */
public class MainApplication extends Application {

    private static MainApplication Instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
    }

    public MainApplication getInstance() {
        return Instance;
    }
}
