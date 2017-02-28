package com.example.pivotal.boomerang_pivotal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.pivotal.boomerang_pivotal.service.NetworkPostTask;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

        EditText personView = (EditText) findViewById(R.id.person_edit);
        String user = personView.getText().toString();

//        new NetworkPostTask().execute("https://boomerang.cfapps.io/opportunity/post?" +
        new NetworkPostTask().execute("http://10.74.18.122:8080/opportunity/post?" +
                "description=" + note +
                "&title=" + URLEncoder.encode(title, "utf-8") +
                "&hours=" + hours +
                "&location=" + URLEncoder.encode(locationName, "utf-8") +
                "&lat=" + location.latitude +
                "&lon=" + location.longitude
        );

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
