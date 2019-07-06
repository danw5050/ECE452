package com.goose.tapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AddNewTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the activity views
        setContentView(R.layout.activity_add_new_tag);
        getSupportActionBar().hide();
//        Button studyTable = findViewById(R.id.studyTable);

        // Setup the activity listeners
//        studyTable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                studyTable();
//            }
//        });
    }

    /*
     * {Remove the temp function}
     */
//    protected void studyTable(){
//        Intent myIntent = new Intent(NFCListActivity.this, StudyTableActivity.class);
//        NFCListActivity.this.startActivity(myIntent);
//    }
}
