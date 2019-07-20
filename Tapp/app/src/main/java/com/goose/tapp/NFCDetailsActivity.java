package com.goose.tapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NFCDetailsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    public static FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the activity views
        setContentView(R.layout.activity_nfc_details);
        TextView nfcTitle = findViewById(R.id.nfcTitle);
        Button showQRCode = findViewById(R.id.showQRCode);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intent_home = new Intent(NFCDetailsActivity.this, MainActivity.class);
                        NFCDetailsActivity.this.startActivity(intent_home);
                        break;
                    case R.id.action_scanQR:
                        Intent intent_scan = new Intent(NFCDetailsActivity.this, QRCodeScannerActivity.class);
                        NFCDetailsActivity.this.startActivity(intent_scan);
                        break;
                    case R.id.action_logout:
                        auth.signOut();
                        user = auth.getCurrentUser();
                        if (user == null) {
                            startActivity(new Intent(NFCDetailsActivity.this, LoginActivity.class));
                            finish();
                        }
                        break;
                }
                return true;
            }
        });

        // Get the nfcDetails to populate activity
        final NFCDetails nfcDetails = (NFCDetails)getIntent().getSerializableExtra("NFC_DETAILS");
        if (nfcDetails == null ) {
            Log.e("NFCDetailsActivity", "Failed to get NFC Details - NFC_DETAILS is empty");
            return;
        } else if(nfcDetails != null){
            CheckBox browserOpener =  findViewById (R.id.browserOpener);
          /*  boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("checkBox1", false);
            browserOpener.setChecked(checked);*/

            CheckBox wifiOpener = findViewById(R.id.wifiOpener);
           /* boolean checked2 = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("checkBox2", false);
            wifiOpener.setChecked(checked2);*/

            CheckBox studyECE452 = findViewById(R.id.studyECE452);
          /*  boolean checked3 = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("checkBox3", false);
            studyECE452.setChecked(checked3);*/

            CheckBox bluetoothOpener = findViewById(R.id.bluetoothOpener);
           /* boolean checked4 = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("checkBox4", false);
            bluetoothOpener.setChecked(checked4);*/

            CheckBox volumeEditor = findViewById(R.id.volumeEditor);
          /*  boolean checked5 = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("checkBox5", false);
            volumeEditor.setChecked(checked5);*/

            CheckBox applicationopener = findViewById(R.id.applicationopener);
           /* boolean checked6 = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("checkBox6", false);
            applicationopener.setChecked(checked6);*/

            CheckBox brightness = findViewById(R.id.brightness);
          /*  boolean checked7 = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("checkBox7", false);
           brightness.setChecked(checked7);*/

            CheckBox potraitMode = findViewById(R.id.potraitMode);
            /*  boolean checked8 = PreferenceManager.getDefaultSharedPreferences(this)
                  .getBoolean("checkBox8", false);
          potraitMode.setChecked(checked8);*/
        }

        // Populate activity with nfcDetails
        nfcTitle.setText(nfcDetails.getTagLocation());


        // Add onClickListeners
        showQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show QR Code for NFC Tag to share
                Intent myIntent = new Intent(NFCDetailsActivity.this, QRCodeGenerationActivity.class);
                myIntent.putExtra("EXTRA_QR_STRING", nfcDetails.getNfcID());
                NFCDetailsActivity.this.startActivity(myIntent);
            }
        });

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.browserOpener:
             /*   PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox1", checked).commit();*/
                break;
        }
    }

    public void onCheckboxClicked2(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.wifiOpener:
               /* PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox2", checked).commit();*/
                break;
        }
    }

    public void onCheckboxClicked3(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.studyECE452:
               /* PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox3", checked).commit();*/
                break;
        }
    }

    public void onCheckboxClicked4(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.bluetoothOpener:
             /*   PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox4", checked).commit();*/
                break;
        }
    }

    public void onCheckboxClicked5(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.volumeEditor:
              /*  PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox5", checked).commit();*/
                break;
        }
    }

    public void onCheckboxClicked6(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.applicationopener:
               /* PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox6", checked).commit();*/
                break;
        }
    }

    public void onCheckboxClicked7(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.brightness:
               /* PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox7", checked).commit();*/
                break;
        }
    }

    public void onCheckboxClicked8(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.potraitMode:
             /*   PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox8", checked).commit();*/
                break;
        }
    }
}