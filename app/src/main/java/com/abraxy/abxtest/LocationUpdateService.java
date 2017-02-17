package com.abraxy.abxtest;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class LocationUpdateService extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String ACTION_UPDATELOCATION = "com.abraxy.abxtest.action.RecieveLocation";
    public static final String ACTION_RECIEVELOCATION = "com.abraxy.abxtest.action.UpdateLocation";
    public static final String ACTION_STARTLOCATION = "com.abraxy.abxtest.action.StartLocation";
    public static final String ACTION_PAUSELOCATION = "com.abraxy.abxtest.action.PauseLocation";
    public static final String ACTION_STOPLOCATION = "com.abraxy.abxtest.action.StopLocation";

    public static final String EXTRA_LOCATION = "com.abraxy.abxtest.extra.Location";

    private GoogleApiClient mGoogleClient;
    private LocationRequest mLocationRequest;
    private PendingIntent mLocationPendingIntent;


    public LocationUpdateService() {
        super("LocationUpdateService");
    }


    /**
     * Connect to the Google Api. This function won't do anything, if the client is already connected.
     * Everything that should be done after starting the api, will go into the onConnect handler.
     */
    protected synchronized void connectGoogleApiClient() {
        Log.v("LocationUpdateService", "connectGoogleApiClient");
        if (mGoogleClient == null) {
            Log.d("LocationUpdateService", "create new google api client");
            mGoogleClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        } else {
            Log.w("LocationUpdateService", "trying to create a already connected api client");
        }
        if (!mGoogleClient.isConnected() && !mGoogleClient.isConnecting()) {
            Log.d("LocationUpdateService", "connect google api client");
            mGoogleClient.connect();
        } else {
            Log.w("LocationUpdateService", "trying to connect a already connected api client");
        }
    }

    protected void disconnectGoogleApiClient() {
        Log.v("LocationUpdateService", "disconnectGoogleApiClient");
        if (mGoogleClient != null && mGoogleClient.isConnected()) {
            Log.d("LocationUpdateService", "disconnecting the google api client");
            mGoogleClient.disconnect();
        } else {
            Log.w("LocationUpdateService", "trying to disconnect a not connected api client");
        }
        mGoogleClient = null;
    }

    protected synchronized LocationRequest getLocationRequest() {
        Log.v("LocationUpdateService", "buildLocationRequest");
        if (mLocationRequest == null) {
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // this has to be high accuracy - otherwise the emulator don't give any updates -question is, if it can be changed to balanced power, if in real device
                    .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                    .setFastestInterval(1000); // 1 second, in milliseconds
        } else {
            Log.w("LocationUpdateService", "trying to create an already valid location request");
        }
        return mLocationRequest;
    }

    protected synchronized PendingIntent getLocationPendingIntent() {
        Log.v("LocationUpdateService", "buildLocationPendingIntent");
        if (mLocationPendingIntent == null) {
            Intent mRequestLocationUpdatesIntent = new Intent(this, LocationUpdateService.class);
            mRequestLocationUpdatesIntent.setAction(LocationUpdateService.ACTION_RECIEVELOCATION);

            // create a PendingIntent
            mLocationPendingIntent = PendingIntent.getService(this, 0,
                    mRequestLocationUpdatesIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            Log.w("LocationUpdateService", "trying to create an already valid pending intent");
        }
        return mLocationPendingIntent;
    }

    //TODO: we have to implement the permission handling, to request the permission, if not permitted
    @SuppressWarnings("MissingPermission")
    protected void startLocationUpdates() {
        Log.v("LocationUpdateService", "startLocationUpdates");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleClient,
                getLocationRequest(),
                getLocationPendingIntent());
    }

    protected void stopLocationUpdates() {
        Log.v("LocationUpdateService", "stopLocationUpdate");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleClient, mLocationPendingIntent);
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("LocationUpdateService", "onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            //noinspection StatementWithEmptyBody
            if (ACTION_UPDATELOCATION.equals(action)) {
                // make sure we don't process the action thrown by us
            } else if (ACTION_RECIEVELOCATION.equals(action)) {
                handleActionRecieveLocation(intent);
            } else if (ACTION_STARTLOCATION.equals(action)) {
                handleActionStartLocation(intent);
            } else if (ACTION_PAUSELOCATION.equals(action)) {
                handleActionPauseLocation(intent);
            } else if (ACTION_STOPLOCATION.equals(action)) {
                handleActionStopLocation(intent);
            }
        }
    }

    /**
     * Handle ACTION_STARTLOCATION in the provided background thread..
     * TODO: the intent should carry an activity to handle any problems
     */
    private void handleActionStartLocation(Intent intent) {
        Log.v("LocationUpdateService", "handleActionStartLocation");
        Log.d("LocationUpdateService", "connecting to api by intent request");
        connectGoogleApiClient();
    }

    /**
     * Handle ACTION_PAUSELOCATION in the provided background thread..
     */
    private void handleActionPauseLocation(Intent intent) {
        Log.v("LocationUpdateService", "handleActionPauseLocation");
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle ACTION_STOPLOCATION in the provided background thread..
     */
    private void handleActionStopLocation(Intent intent) {
        Log.v("LocationUpdateService", "handleActionStopLocation");
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle ACTION_RECIEVELOCATION in the provided background thread.
     * We give the Intent to the actionhandler, as locationupdates is using
     * LocationResult to verify the Intent
     */
    private void handleActionRecieveLocation(Intent intent) {
        Log.v("LocationUpdateService", "handleActionRecieveLocation");
        if (LocationResult.hasResult(intent)) {
            LocationResult locationResult = LocationResult.extractResult(intent);
            Location location = locationResult.getLastLocation();
            if (location != null) {
                Log.d("LocationUpdateService", "handleActionReceiveLocation: accuracy(" + location.getAccuracy() + ") lat(" + location.getLatitude() + ") lon(" + location.getLongitude() + ")");

                // Valid Location recieved - throw a broadcast to inform, whoever is interested
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(ACTION_UPDATELOCATION);
                broadcastIntent.putExtra(EXTRA_LOCATION, location);
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            } else {
                Log.d("LocationUpdateService", "handleActionReceiveLocation - location == null");
            }
        } else {
            Log.d("LocationUpdateService", "handleActionReceiveLocation - no location result");
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v("LocationUpdateService", "onConnected");
        Log.d("LocationUpdateService", "starting location updates by on connected");
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("LocationUpdateService", "onConnectionSuspended");
        Log.d("LocationUpdateService", "suspending location updates by on suspended");
        stopLocationUpdates();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("LocationUpdateService", "onConnectionFailed");
        Log.w("LocationUpdateService", "failed to connect to the google api");
        //TODO: add failure handling
    }
}
