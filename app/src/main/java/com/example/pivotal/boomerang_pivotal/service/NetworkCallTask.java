package com.example.pivotal.boomerang_pivotal.service;

import android.os.AsyncTask;

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
    protected void onPostExecute(String result) {
        System.out.println(">>>>>>>>>>> Network Task: " + result);
    }
}