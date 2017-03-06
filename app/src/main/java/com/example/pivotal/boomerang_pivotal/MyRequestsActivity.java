package com.example.pivotal.boomerang_pivotal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pivotal.boomerang_pivotal.model.OpportunitiesResponse;
import com.example.pivotal.boomerang_pivotal.model.Opportunity;
import com.example.pivotal.boomerang_pivotal.service.ApiEndpointService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRequestsActivity extends AppCompatActivity {

    List<Opportunity> opportunities = new ArrayList<>();
    String user;

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
            user = params.getString("user");
        }

        getUsersRequests();
    }

    private void getUsersRequests() {
        String BASE_URL = "https://boomerang-ria.cfapps.io/";
        final Intent intent = new Intent(this, HelpRequestActivity.class);
        final OpportunityAdapter adapter = new OpportunityAdapter(this, opportunities);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiEndpointService apiService = retrofit.create(ApiEndpointService.class);
        Call<OpportunitiesResponse> call = apiService.getOpportunitiesByUser(user);
        call.enqueue(new Callback<OpportunitiesResponse>() {

            @Override
            public void onResponse(Call<OpportunitiesResponse> call, Response<OpportunitiesResponse> response) {
                opportunities = response.body().get_embedded().getOpportunities();
                adapter.addAll(opportunities);
                ListView listView = (ListView) findViewById(R.id.mobile_list);
                listView.setClickable(true);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Opportunity selectedOpportunity = adapter.getItem(position);
                        intent.putExtra("opportunity", selectedOpportunity);
                        intent.putExtra("action", "Update");
                        startActivity(intent);
                    }
                });
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<OpportunitiesResponse> call, Throwable throwable) {
                Log.i("GetOpportunitiesByUser", "An error occurred: " + throwable.getLocalizedMessage());
            }
        });
    }
}
