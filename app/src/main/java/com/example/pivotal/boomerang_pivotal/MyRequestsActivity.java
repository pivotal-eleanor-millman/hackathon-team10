package com.example.pivotal.boomerang_pivotal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.pivotal.boomerang_pivotal.model.Opportunity;

public class MyRequestsActivity extends AppCompatActivity {

    Opportunity opportunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle params = getIntent().getExtras();
        if(params != null) {
            opportunity = (Opportunity) params.getSerializable("opportunity");
            TextView nameTextView = (TextView)findViewById(R.id.requestTitle);
            nameTextView.setText(opportunity.getTitle());
            TextView descriptionTextView = (TextView)findViewById(R.id.requestDescription);
            descriptionTextView.setText(opportunity.getDescription());
        }
    }

    public void editRequest(View view) {
        final Intent intent = new Intent(this, HelpRequestActivity.class);
        intent.putExtra("opportunity", opportunity);
        startActivity(intent);
    }
}
