package com.example.pivotal.boomerang_pivotal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.pivotal.boomerang_pivotal.service.NetworkPostTask;

public class HelpRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onAskForHelpClicked(View view) {
        EditText noteView = (EditText) findViewById(R.id.note_edit);
        String note = noteView.getText().toString();

        EditText titleView = (EditText) findViewById(R.id.title_edit);
        String title = titleView.getText().toString();

        EditText timeView = (EditText) findViewById(R.id.time_edit);
        String hours = timeView.getText().toString();

        EditText personView = (EditText) findViewById(R.id.person_edit);
        String user = personView.getText().toString();

        EditText locationView = (EditText) findViewById(R.id.location_edit);
        String location = locationView.getText().toString();

        new NetworkPostTask().execute("https://boomerang.cfapps.io/opportunity");

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
