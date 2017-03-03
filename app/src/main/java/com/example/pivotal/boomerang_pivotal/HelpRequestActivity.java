package com.example.pivotal.boomerang_pivotal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.pivotal.boomerang_pivotal.model.Opportunity;
import com.example.pivotal.boomerang_pivotal.service.ApiEndpointService;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelpRequestActivity extends AppCompatActivity {

    Opportunity opportunity = new Opportunity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Fragment from Google to autocomplete location
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("What's your address?");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng location = place.getLatLng();
                opportunity.setLatitude(location.latitude);
                opportunity.setLongitude(location.longitude);
                opportunity.setAddress(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("PlaceAutocomplete", "An error occurred: " + status);
            }
        });
        prepopulateOpportunity(autocompleteFragment);
    }

    public void onAskForHelpClicked(View view) throws UnsupportedEncodingException {
        EditText noteView = (EditText) findViewById(R.id.note_edit);
        String note = noteView.getText().toString();

        EditText titleView = (EditText) findViewById(R.id.title_edit);
        String title = titleView.getText().toString();

        EditText timeView = (EditText) findViewById(R.id.time_edit);
        String hours = timeView.getText().toString();

        String BASE_URL = "https://boomerang-ria.cfapps.io/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        opportunity.setHours(hours);
        opportunity.setTitle(title);
        opportunity.setDescription(note);

        final Intent intent = new Intent(this, MyRequestsActivity.class);
        ApiEndpointService apiService = retrofit.create(ApiEndpointService.class);
        Call<Opportunity> call;
        Bundle params = getIntent().getExtras();

        //Determine whether we should create or update
        if (params.getString("action") != null && params.getString("action").equals("Update")) {
            call = apiService.updateOpportunity(opportunity, opportunity.getId());
        } else {
            call = apiService.createOpportunity(opportunity);
        }
        call.enqueue(new Callback<Opportunity>() {
            @Override
            public void onResponse(Call<Opportunity> call, Response<Opportunity> response) {
                Opportunity opportunityResult = response.body();
                intent.putExtra("opportunity", opportunityResult);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Opportunity> call, Throwable throwable) {
                Log.i("CreateOpportunity", "An error occurred: " + throwable.getLocalizedMessage());
            }
        });
    }

    private void prepopulateOpportunity(PlaceAutocompleteFragment autocompleteFragment){
        Bundle params = getIntent().getExtras();
        if(params != null && params.getSerializable("opportunity") != null) {
            opportunity = (Opportunity) params.getSerializable("opportunity");
            EditText noteView = (EditText) findViewById(R.id.note_edit);
            noteView.setText(opportunity.getDescription());

            EditText titleView = (EditText) findViewById(R.id.title_edit);
            titleView.setText(opportunity.getTitle());

            EditText timeView = (EditText) findViewById(R.id.time_edit);
            timeView.setText(opportunity.getHours());

            ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setText(opportunity.getAddress());
        }
    }
}
