package com.example.pivotal.boomerang_pivotal.service;

import android.os.AsyncTask;

import com.example.pivotal.boomerang_pivotal.model.Opportunity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkCallTask extends AsyncTask<String, Void, String> {

    String result;

    public NetworkCallTask(String result) {
        this.result = result;
    }

    protected String doInBackground(String... addresses) {
        StringBuilder total = new StringBuilder();
        InputStream in = null;
        try {
            // 1. Declare a URL Connection
            URL url = new URL(addresses[0]);
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
        Opportunity result = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            result = getOpportunityFromParsedJson(jsonObject);

            if (result == null) {
                System.out.println(">>>>>>>>>>> Sth bad happened ");
            }
            System.out.println(">>>>>>>>>>> title: " + result.getTitle());
            System.out.println(">>>>>>>>>>> address: " + result.getAddress());
            System.out.println(">>>>>>>>>>> description: " + result.getDescription());
            System.out.println(">>>>>>>>>>> hours: " + result.getHours());
            System.out.println(">>>>>>>>>>> latitude: " + result.getLatitude());
            System.out.println(">>>>>>>>>>> longitude: " + result.getLongitude());
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