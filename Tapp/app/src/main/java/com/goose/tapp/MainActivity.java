package com.goose.tapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Button addNewTag = findViewById(R.id.addNewTag);
        Button scanQRCode = findViewById(R.id.scanQRCode);
        mainActivityRootView = findViewById(R.id.mainActivityRootView);



        // Get the user currently logged in
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // If the user is not logged in show login page
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

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

        addNewTag.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, NewNFCActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        } );

        scanQRCode.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, QRCodeScannerActivity.class);
                MainActivity.this.startActivity(myIntent);
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
                            nfcDetails.setNfcID(dataSnapshot.getKey());

                            // Replace the nfc tag from list if already exists
                            Boolean updated = false;
                            for (int i = 0; i < userNFCList.size(); i++) {
                                if (userNFCList.get(i).getNfcID() == nfcDetails.getNfcID()) {
                                    userNFCList.set(i, nfcDetails);
                                    updated = true;
                                    break;
                                }
                            }

                            // Add the nfc tag to the list of of all nfc tags if not updated
                            if (!updated) {
                                userNFCList.add(nfcDetails);
                            }

                            // Update the recyclerview with the new nfc tag
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
                // Open details about the clicked NFC tag
                Intent myIntent = new Intent(MainActivity.this, NFCDetailsActivity.class);
                myIntent.putExtra("NFC_DETAILS",  userNFCList.get(position));
                MainActivity.this.startActivity(myIntent);
            }

            @Override
            public void onItemLongClick(final int position, View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // Delete the NFC from recyclerview and firebase
                                databaseReference.child("Users").child(user.getUid()).child(userNFCList.get(position).getNfcID()).removeValue();
                                userNFCList.remove(position);
                                usersNFCListRecyclerViewAdapter.notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete this NFC?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("Cancel", dialogClickListener)
                        .show();
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
