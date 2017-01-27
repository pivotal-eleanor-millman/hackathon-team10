package com.example.pivotal.boomerang_pivotal.service;

import android.os.AsyncTask;

import com.example.pivotal.boomerang_pivotal.model.Opportunity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MultipleObjectNetworkCallTask extends AsyncTask<String, Void, String> {

    String result;
    GoogleMap googleMap;


    public MultipleObjectNetworkCallTask(String result, GoogleMap googleMap) {
        this.result = result;
        this.googleMap = googleMap;
    }

    protected String doInBackground(String... parameters) {
        StringBuilder total = new StringBuilder();
        InputStream in = null;
        try {
            // 1. Declare a URL Connection
            URL url = new URL(parameters[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 2. Open InputStream to connection
            conn.connect();
            in = conn.getInputStream();
            // 3.
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if(in != null)
//                in.close();
        }
        return total.toString();
    }

    // Fires after the task is completed, displaying the bitmap into the ImageView
    @Override
    protected void onPostExecute(String json) {
        System.out.println(">>>>>>>>>>> Network Task: " + json);
        List<Opportunity> result = null;
        try {
            JSONArray jsonarray = new JSONArray(json);
            result = new ArrayList<>();
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonObject = jsonarray.getJSONObject(i);

                Opportunity opportunity = getOpportunityFromParsedJson(jsonObject);
                result.add(opportunity);

                if (result == null) {
                    System.out.println(">>>>>>>>>>> Sth bad happened ");
                }

                LatLng op1 = new LatLng(opportunity.getLatitude(), opportunity.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(op1).title(opportunity.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Opportunity getOpportunityFromParsedJson(JSONObject jsonObject) {
        try {
            Opportunity opportunity = new Opportunity();
            opportunity.setTitle(jsonObject.getString("title"));
            opportunity.setAddress(jsonObject.getString("address"));
            opportunity.setDescription(jsonObject.getString("description"));
            opportunity.setHours(jsonObject.getString("hours"));
            opportunity.setLatitude(jsonObject.getDouble("latitude"));
            opportunity.setLongitude(jsonObject.getDouble("longitude"));

            return opportunity;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}