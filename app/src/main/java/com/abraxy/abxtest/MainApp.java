package com.abraxy.abxtest;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Main Application to implement the GoogleApi connection
 * Created by klwi4251 on 16.02.2017.
 */

public class MainApp extends Application implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleClient;
    private LocationRequest mLocationRequest;
    private PendingIntent mLocationPendingIntent;

    protected synchronized void buildGoogleApiClient() {
        Log.v("MainApp", "buildGoogleApiClient");
        mGoogleClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected synchronized void buildLocationRequest() {
        Log.v("MainApp", "buildLocationRequest");
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // this has to be high accuracy - otherwise the emulator don't give any updates -question is, if it can be changed to balanced power, if in real device
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds
    }

    protected synchronized void buildLocationPendingIntent() {
        Log.v("MainApp", "buildLocationPendingIntent");
        Intent mRequestLocationUpdatesIntent = new Intent(this, LocationUpdateService.class);
        mRequestLocationUpdatesIntent.setAction(LocationUpdateService.ACTION_RECIEVELOCATION);

        // create a PendingIntent
        mLocationPendingIntent = PendingIntent.getService(getApplicationContext(), 0,
                mRequestLocationUpdatesIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //TODO: we have to implement the permission handling, to request the permission, if not permitted
    @SuppressWarnings("MissingPermission")
    protected void startLocationUpdates() {
        Log.v("MainApp", "startLocationUpdates");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleClient,
                mLocationRequest,
                mLocationPendingIntent);
    }

    protected void stopLocationUpdate() {
        Log.v("MainApp", "stopLocationUpdate");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleClient, mLocationPendingIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("MainApp", "onCreate");
        Intent intent = new Intent(this, LocationUpdateService.class);
        intent.setAction(LocationUpdateService.ACTION_STARTLOCATION);
        startService(intent);
//        buildGoogleApiClient();
//        buildLocationRequest();
//        buildLocationPendingIntent();
//        mGoogleClient.connect();
    }

    @Override
    public void onTerminate() {
        Log.v("MainApp", "onTerminate");
        Intent intent = new Intent(this, LocationUpdateService.class);
        intent.setAction(LocationUpdateService.ACTION_STOPLOCATION);
        startService(intent);

//        if (mGoogleClient != null && mGoogleClient.isConnected()) {
//            mGoogleClient.disconnect();
//        }
//        mGoogleClient = null;
//        super.onTerminate();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v("MainApp", "onConnected");
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("MainApp", "onConnectionSuspended");
        stopLocationUpdate();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("MainApp", "onConnectionFailed");
        //TODO handle failure of connection to the google api - there are tutorials covering this
    }
}
