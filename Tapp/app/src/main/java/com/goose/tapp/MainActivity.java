package com.goose.tapp;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter;
    private RelativeLayout mainActivityRootView;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the activity views
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Button studyTable = findViewById(R.id.studyTable);
        Button logout = findViewById(R.id.logout);
        mainActivityRootView = findViewById(R.id.mainActivityRootView);

        // Ensure user is logged in
        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        // Check the status of NFC in device
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // No NFC in device
            nfcError(getApplicationContext().getString(R.string.no_nfc));
        } else if (!mNfcAdapter.isEnabled()) {
            // NFC disabled
            nfcError(getApplicationContext().getString(R.string.nfc_disabled));
        }


        // Setup the activity listeners
        studyTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyTable();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        });
    }

    /*
     * {Remove the temp function}
     */
    protected void studyTable(){
        Intent myIntent = new Intent(MainActivity.this, StudyTableActivity.class);
        MainActivity.this.startActivity(myIntent);
    }


    /*
     * nfcError called when there is an error or problem with the NFC status on the users mobile device
     */
    public void  nfcError(String errorType) {
        Snackbar snackbar;
        snackbar = Snackbar
                .make(mainActivityRootView, errorType, Snackbar.LENGTH_INDEFINITE)
                .setAction(getApplicationContext().getString(R.string.dismiss_nfc_error), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {}
                });
        snackbar.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
