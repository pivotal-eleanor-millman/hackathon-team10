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

//        try {
//            URL url = new URL("https://boomerang.cfapps.io/opportunity"); //Enter URL here
//            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
//            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
//            httpURLConnection.connect();
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("para_1", "arg_1");
//
//            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//            wr.writeBytes("hello");
//            wr.flush();
//            wr.close();
//
//            httpURLConnection.getInputStream();

//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
    }
}