package com.abraxy.abxtest;

import android.app.Application;
import android.util.Log;

/**
 * Main Application to implement the GoogleApi connection
 * Created by klwi4251 on 16.02.2017.
 */

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("MainApp", "onCreate");
    }

    @Override
    public void onTerminate() {
        Log.v("MainApp", "onTerminate");
        super.onTerminate();
    }
}
