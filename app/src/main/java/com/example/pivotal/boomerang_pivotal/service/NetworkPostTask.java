package com.example.pivotal.boomerang_pivotal.service;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;


public class NetworkPostTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {

        StringBuilder total = new StringBuilder();
        InputStream in = null;
        try {
            // 1. Declare a URL Connection
            URL url = new URL(params[0]);
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
            System.out.println(">>>>> Response: " + total.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if(in != null)
//                in.close();
        }
        return total.toString();
    }

//    public NetworkPostTask() {
//
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//
//    @Override
//    protected String doInBackground(String... params) {
//
//        String urlString = params[0]; // URL to call
//
//        String resultToDisplay = "";
//        StringBuilder total = new StringBuilder();
//
//
//        InputStream in = null;
//        try {
//
//            URL url = new URL(urlString);
//
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//            in = new BufferedInputStream(urlConnection.getInputStream());
//
//
//        } catch (Exception e) {
//
//            System.out.println(e.getMessage());
//
//            return e.getMessage();
//
//        }
//
//        try {
//            BufferedReader r = new BufferedReader(new InputStreamReader(in));
//            String line;
//
//            while ((line = r.readLine()) != null) {
//                total.append(line);
//            }
//            in.close();
//            System.out.println(">>>>> Response: " + total.toString());
//            //to [convert][1] byte stream to a string
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return total.toString();
//    }
//
//
//    @Override
//    protected void onPostExecute(String result) {
//        //Update the UI
//        System.out.println("result " + result);
//    }

    }