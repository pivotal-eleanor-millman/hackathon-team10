package com.example.pivotal.boomerang_pivotal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MyRequestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title = "Title";
        String description = "Description";
        Bundle params = getIntent().getExtras();
        if(params != null) {
            title = params.getString("title");
            description = params.getString("description");
        }

        TextView nameTextView = (TextView)findViewById(R.id.requestTitle);
        nameTextView.setText(title);
        TextView descriptionTextView = (TextView)findViewById(R.id.requestDescription);
        descriptionTextView.setText(description);
    }
}
