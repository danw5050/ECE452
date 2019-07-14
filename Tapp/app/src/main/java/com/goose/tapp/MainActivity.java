package com.goose.tapp;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter;

    // Required global layouts
    private RelativeLayout mainActivityRootView;
    private RecyclerView nfcListRecyclerView;


    //Users NFC RecyclerView
    List<NFCDetails> userNFCList = new ArrayList<>();
    UsersNFCListRecyclerViewAdapter usersNFCListRecyclerViewAdapter;

    // Firebase Database
    DatabaseReference databaseReference;

    // Firebase Authentication
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    // Reference to current user for entire application
    public static FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the activity views
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        nfcListRecyclerView = findViewById(R.id.nfcListListView);
        Button logout = findViewById(R.id.logout);
        mainActivityRootView = findViewById(R.id.mainActivityRootView);



        // Get the user currently logged in
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // Ensure user is logged in
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

        // Setup on click listeners
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the user out
                auth.signOut();
            }
        });



        //Show all nfc tags belonging to user
        nfcListRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Get all nfc tags belonging to user
        databaseReference.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // Go through every NFC belonging to user
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    // Find details for every NFC belonging to the user
                    databaseReference.child("NFCIds").child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Add the nfc tag details
                            NFCDetails nfcDetails = dataSnapshot.getValue(NFCDetails.class);
                            userNFCList.add(nfcDetails);
                            usersNFCListRecyclerViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        // Create the adapter for nfcListRecyclerView
        usersNFCListRecyclerViewAdapter = new UsersNFCListRecyclerViewAdapter(MainActivity.this, userNFCList);
        // Add onclick listener for items in nfcListRecyclerView
        usersNFCListRecyclerViewAdapter.setOnItemClickListener(new UsersNFCListRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("test", "onItemClick position: " + position);

            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d("test", "onItemLongClick pos: " + position);
            }
        });

        nfcListRecyclerView.setAdapter(usersNFCListRecyclerViewAdapter);
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

}
