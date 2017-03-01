package com.example.pivotal.boomerang_pivotal;

import android.content.Intent;
import android.os.Bundle;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelpRequestActivity extends AppCompatActivity {

    LatLng location;
    String locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fragment from Google to autocomplete location
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("What's your address?");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("PlaceAutocomplete", "Place: " + place.getName());
                location = place.getLatLng();
                locationName = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("PlaceAutocomplete", "An error occurred: " + status);
            }
        });
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

        Opportunity opportunity = new Opportunity();
        opportunity.setLatitude(location.latitude);
        opportunity.setLongitude(location.longitude);
        opportunity.setAddress(locationName);
        opportunity.setHours(hours);
        opportunity.setTitle(title);
        opportunity.setDescription(note);

        final Intent intent = new Intent(this, MyRequestsActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("description", note);
        ApiEndpointService apiService = retrofit.create(ApiEndpointService.class);
        Call<ResponseBody> call = apiService.createOpportunity(opportunity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.i("CreateOpportunity", "An error occurred: " + throwable.getLocalizedMessage());
            }
        });
    }
}
