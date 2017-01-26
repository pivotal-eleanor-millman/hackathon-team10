package com.example.pivotal.boomerang_pivotal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.pivotal.boomerang_pivotal.service.NetworkCallTask;
import com.example.pivotal.boomerang_pivotal.util.NetworkUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final static String URL_NEARBY = "https://boomerang.cfapps.io/nearby";

    private GoogleMap mMap;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        getNearbyOpportunities(connectivityManager);
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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void getNearbyOpportunities(ConnectivityManager connectivityManager) {
        //TODO: Get GPS location
        String latitude = "43.650596";
        String longitude = "-79.374934";
        String radius = "4000";

        if (NetworkUtils.isNetworkAvailable(connectivityManager) && NetworkUtils.isOnline()) {
            String url = URL_NEARBY + "?latitude=" + latitude +
                    "&longitude=" + longitude + "&radius=" + radius;
            new NetworkCallTask(result).execute(url);

            System.out.println(">>>>>> Activity: " + result);
        } else {
            //FIXME: handle
            System.out.println("error");
        }
    }
}
