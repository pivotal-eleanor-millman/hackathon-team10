package com.example.pivotal.boomerang_pivotal;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pivotal.boomerang_pivotal.model.Opportunity;
import com.example.pivotal.boomerang_pivotal.service.INetworkCallTask;
import com.example.pivotal.boomerang_pivotal.service.NetworkCallTask;
import com.example.pivotal.boomerang_pivotal.util.NetworkUtils;

public class MainActivity extends AppCompatActivity implements INetworkCallTask {

//    private final static String URL_NEARBY = "https://boomerang.cfapps.io/nearby";
    private final static String URL_NEARBY = "http://10.74.18.122:8080/nearby";

    ConnectivityManager mConnectivityManager;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    Location mCurrentLocation = new Location("");
    Opportunity mNearestOpportunity = new Opportunity();
    Opportunity mLatestOpportunity;

    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startNotificationChecks();
        System.out.println(">>>on Create");

        mCurrentLocation.setLatitude(43.646739);
        mCurrentLocation.setLongitude(-79.375116);
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mCurrentLocation = location;
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {} else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }

    }

    public void postResult(Opportunity opportunity) {
        mLatestOpportunity = opportunity;
        System.out.println("Got result " + opportunity);
    }

    private void getNearestOpportunity() {
//        String latitude = String.valueOf(43.6534274);
        String latitude = String.valueOf(mCurrentLocation.getLatitude());
        String longitude = String.valueOf(mCurrentLocation.getLongitude());
//        String longitude = String.valueOf(-79.3777415);
        String radius = "4000";

        if (NetworkUtils.isNetworkAvailable(mConnectivityManager) && NetworkUtils.isOnline()) {
            String url = URL_NEARBY + "?latitude=" + latitude +
                    "&longitude=" + longitude + "&radius=" + radius;
            NetworkCallTask networkCall = new NetworkCallTask(null);
            networkCall.delegate = this;
            networkCall.execute(url);

            System.out.println(">>>>>> Activity: ");
        } else {
            System.out.println("error");
        }
//        return mTestOpportunity;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        }
    }

    private void startNotificationChecks() {
        final Handler h = new Handler();
        final int delay = 60000; //milliseconds
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                getNearestOpportunity();
                System.out.println("Getting latest opportunity");

                if (mLatestOpportunity != null && !mLatestOpportunity.getTitle().equalsIgnoreCase(mNearestOpportunity.getTitle())) {
                    System.out.println("Getting inside");
                    mNearestOpportunity = mLatestOpportunity;
                    makeNotification();
                }
                h.postDelayed(this, delay);
            }
        }, delay);
    }

    private void makeNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_stat_boom_not_ic)
                        .setContentTitle("There is a new volunteer opportunity!")
                        .setContentText(mNearestOpportunity.getTitle());

        Intent resultIntent = new Intent(this, MapActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout needHelpLayout = (LinearLayout) findViewById(R.id.need_help_layout);
        needHelpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNeedHelpLayoutClicked(v);
            }
        });

        LinearLayout wantToHelpLayout = (LinearLayout) findViewById(R.id.want_help_layout);
        wantToHelpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWantToHelpLayoutClicked(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onNeedHelpLayoutClicked(View view) {
        Intent intent = new Intent(this, HelpRequestActivity.class);
        startActivity(intent);
    }

    public void onWantToHelpLayoutClicked(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
