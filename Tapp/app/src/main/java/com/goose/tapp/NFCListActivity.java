package com.goose.tapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NFCListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_list);
        getSupportActionBar().hide();

    }

    protected void studyTable(View view){
        Intent myIntent = new Intent(NFCListActivity.this, StudyTableActivity.class);
        NFCListActivity.this.startActivity(myIntent);
    }
}
