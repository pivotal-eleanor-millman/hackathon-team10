package com.example.pivotal.boomerang_pivotal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.pivotal.boomerang_pivotal.model.Opportunity;
import com.example.pivotal.boomerang_pivotal.service.ApiEndpointService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestDetailsActivity extends AppCompatActivity {

    Opportunity opportunity;

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
            opportunity = (Opportunity) params.getSerializable("opportunity");
            TextView nameTextView = (TextView)findViewById(R.id.requestTitle);
            nameTextView.setText(opportunity.getTitle());
            TextView descriptionTextView = (TextView)findViewById(R.id.requestDescription);
            descriptionTextView.setText(opportunity.getDescription());
            TextView addressTextView = (TextView)findViewById(R.id.requestAddress);
            addressTextView.setText(opportunity.getAddress());
            TextView hoursTextView = (TextView)findViewById(R.id.requestHours);
            hoursTextView.setText(opportunity.getHours());
        }
    }

    public void onVolunteerClicked(View view) {
        opportunity.setAccepted(true);

        String BASE_URL = "http://10.74.18.122:8080/";
//        String BASE_URL = "https://boomerang-ria.cfapps.io/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Intent intent = new Intent(this, MainActivity.class);;
        ApiEndpointService apiService = retrofit.create(ApiEndpointService.class);
        Call<Opportunity> call = apiService.updateOpportunity(opportunity, opportunity.getId());
        call.enqueue(new Callback<Opportunity>() {
            @Override
            public void onResponse(Call<Opportunity> call, Response<Opportunity> response) {
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Opportunity> call, Throwable throwable) {
                Log.i("AcceptOpportunity", "An error occurred: " + throwable.getLocalizedMessage());
            }
        });
    }
}
