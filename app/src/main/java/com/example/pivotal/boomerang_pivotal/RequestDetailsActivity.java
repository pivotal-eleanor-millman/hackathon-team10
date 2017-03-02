package com.example.pivotal.boomerang_pivotal;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class RequestDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle params = getIntent().getExtras();
        if(params != null) {
            TextView nameTextView = (TextView)findViewById(R.id.requestTitle);
            nameTextView.setText(params.getString("title"));
            TextView descriptionTextView = (TextView)findViewById(R.id.requestDescription);
            descriptionTextView.setText(params.getString("description"));
            TextView addressTextView = (TextView)findViewById(R.id.requestAddress);
            addressTextView.setText(params.getString("address"));
            TextView hoursTextView = (TextView)findViewById(R.id.requestHours);
            hoursTextView.setText(params.getString("hours"));
        }
    }
}
