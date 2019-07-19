package com.goose.tapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewNFCActivity  extends AppCompatActivity {

    DatabaseReference databaseReference;

    TextView newTagName;
    TextView newTagLocation;
    Button saveNewNFC;
    private FirebaseAuth auth;
    public static FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the activity views
        setContentView(R.layout.activity_new_nfc);
        newTagName = findViewById(R.id.newTagName);
        newTagLocation = findViewById(R.id.newTagLocation);
        saveNewNFC = findViewById(R.id.saveNewNFC);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intent_home = new Intent(NewNFCActivity.this, MainActivity.class);
                        NewNFCActivity.this.startActivity(intent_home);
                        break;
                    case R.id.action_scanQR:
                        Intent intent_scan = new Intent(NewNFCActivity.this, QRCodeScannerActivity.class);
                        NewNFCActivity.this.startActivity(intent_scan);
                        break;
                    case R.id.action_logout:
                        auth.signOut();
                        user = auth.getCurrentUser();
                        if (user == null) {
                            startActivity(new Intent(NewNFCActivity.this, LoginActivity.class));
                            finish();
                        }
                        break;
                }
                return true;
            }
        });

        saveNewNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create random id for NFC TAG
                String nfcID = GenerateRandomString.randomString(10);

                // Set the details for NFC TAG
                NFCDetails nfcDetails = new NFCDetails();
                nfcDetails.setName(newTagName.getText().toString());
                nfcDetails.setTagLocation(newTagLocation.getText().toString());

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



