package com.abraxy.abxtest;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.LocationResult;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class LocationUpdateService extends IntentService {
    public static final String ACTION_UPDATELOCATION = "com.abraxy.abxtest.action.RecieveLocation";
    public static final String ACTION_RECIEVELOCATION = "com.abraxy.abxtest.action.UpdateLocation";

    public static final String EXTRA_LOCATION = "com.abraxy.abxtest.extra.Location";

    public LocationUpdateService() {
        super("LocationUpdateService");
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
            }
        }
    }


//TODO: let the service manage itself by static functions - question is, if we should keep
// the reference to the api client or request it as needed.

    /**
     * Handle ACTION_UPDATELOCATION in the provided background thread..
     */
    private void handleActionUpdateLocation(Intent intent) {
        Log.v("LocationUpdateService", "handleActionUpdateLocation");
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
                Log.w("LocationUpdateService", "handleActionReceiveLocation - location == null");
            }
        } else {
            Log.w("LocationUpdateService", "handleActionReceiveLocation - no location result");
        }

    }
}
