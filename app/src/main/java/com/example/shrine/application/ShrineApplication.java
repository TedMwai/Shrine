package com.example.shrine.application;

import android.app.Application;
import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;

public class ShrineApplication extends Application {
    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context mAppContext) {
        appContext = mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}