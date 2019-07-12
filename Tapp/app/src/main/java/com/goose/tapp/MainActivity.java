package com.goose.tapp;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter;
    private ConstraintLayout loginActivityView;
    private DatabaseReference mDatabase;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the activity views
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        loginActivityView = findViewById(R.id.loginActivityView);
        Button loginButton = findViewById(R.id.loginButton);

        // Setup listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });

        // Check the status of NFC in device
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // No NFC in device
            nfcError(getApplicationContext().getString(R.string.no_nfc));
        } else if (!mNfcAdapter.isEnabled()) {
            // NFC disabled
            nfcError(getApplicationContext().getString(R.string.nfc_disabled));
        }
    }

    /*
     * login called when user successfully logs in
     */
    protected void login(){
        Intent myIntent = new Intent(MainActivity.this, NFCListActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    /*
     * nfcError called when there is an error or problem with the NFC status on the users mobile device
     */
    public void  nfcError(String errorType) {
        Snackbar snackbar;
        snackbar = Snackbar
                .make(loginActivityView, errorType, Snackbar.LENGTH_INDEFINITE)
                .setAction(getApplicationContext().getString(R.string.dismiss_nfc_error), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {}
                });
        snackbar.show();
    }
}
