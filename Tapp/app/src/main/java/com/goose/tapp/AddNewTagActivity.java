package com.goose.tapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddNewTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the activity views
        setContentView(R.layout.activity_add_new_tag);
        getSupportActionBar().hide();

    }

}
