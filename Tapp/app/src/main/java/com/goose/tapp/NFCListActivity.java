package com.goose.tapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NFCListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_list);
        getSupportActionBar().hide();

        Button studyTable = (Button) findViewById(R.id.studyTable);
        studyTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyTable();
            }
        });
    }

    protected void studyTable(){
        Intent myIntent = new Intent(NFCListActivity.this, StudyTableActivity.class);
        NFCListActivity.this.startActivity(myIntent);
    }
}
