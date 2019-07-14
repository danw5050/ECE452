package com.goose.tapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewNFCActivity  extends AppCompatActivity {

    DatabaseReference databaseReference;

    TextView newTagName;
    TextView newTagLocation;
    Button saveNewNFC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the activity views
        setContentView(R.layout.activity_new_nfc);
        newTagName = findViewById(R.id.newTagName);
        newTagLocation = findViewById(R.id.newTagLocation);
        saveNewNFC = findViewById(R.id.saveNewNFC);

        saveNewNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create random id for NFC TAG
                String nfcID = GenerateRandomString.randomString(10);

                // Set the details for NFC TAG
                NFCDetails nfcDetails = new NFCDetails();
                nfcDetails.setName(newTagName.getText().toString());
                nfcDetails.setTagLocation(newTagName.getText().toString());

                // Save Details within firebase
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("NFCIds").child(nfcID).setValue(nfcDetails);
                databaseReference.child("Users").child(MainActivity.user.getUid()).child(nfcID).setValue(1);

                // Write to NFC
                Intent myIntent = new Intent(NewNFCActivity.this, NFCWriteActivity.class);
                myIntent.putExtra("EXTRA_NFC_STRING", nfcID);
                NewNFCActivity.this.startActivity(myIntent);
            }
        });
    }
}



