package com.goose.tapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NewNFCTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO - connect with firebase to get the NFCID
        setContentView(R.layout.activity_new_nfc_tag);
        getSupportActionBar().hide();
    }

}
