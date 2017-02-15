package com.abraxy.abxtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ulm = new LatLng(48.3744, 10.0136);
        mMap.addMarker(new MarkerOptions().position(ulm).title("Marker in Neu-Ulm"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ulm));

    }

    public void onCharButton(View view) {
        Log.v("MapsActivity", "onCharButton");
        Intent intent = new Intent(this, CharActivity.class);
        startActivity(intent);
    }

    public void onNearButton(View view) {
        Log.v("MapsActivity", "onNearButton");
        Intent intent = new Intent(this, NearActivity.class);
        startActivity(intent);
    }

    public void onInvButton(View view) {
        Log.v("MapsActivity", "onInvButton");
        Intent intent = new Intent(this, InvActivity.class);
        startActivity(intent);
    }
}
