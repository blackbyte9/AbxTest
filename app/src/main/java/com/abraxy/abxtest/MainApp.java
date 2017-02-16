package com.abraxy.abxtest;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Main Application to implement the GoogleApi connection
 * Created by klwi4251 on 16.02.2017.
 */

public class MainApp extends Application implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleClient;

    protected synchronized void buildGoogleApiClient() {
        Log.v("MainApp", "buildGoogleApiClient");
        mGoogleClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("MainApp", "onCreate");
        buildGoogleApiClient();
        mGoogleClient.connect();
    }

    @Override
    public void onTerminate() {
        Log.v("MainApp", "onTerminate");
        if (mGoogleClient != null && mGoogleClient.isConnected()) {
            mGoogleClient.disconnect();
        }
        mGoogleClient = null;
        super.onTerminate();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v("MainApp", "onConnected");
        //TODO start the location update service
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("MainApp", "onConnectionSuspended");
        //TODO stop the location update service
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("MainApp", "onConnectionFailed");
        //TODO handle failure of connection to the google api - there are tutorials covering this
    }
}
