package com.goose.tapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.goose.tapp.NFCDetails.Application;
import com.goose.tapp.NFCDetails.Bluetooth;
import com.goose.tapp.NFCDetails.Brightness;
import com.goose.tapp.NFCDetails.Browser;
import com.goose.tapp.NFCDetails.NFCDetails;
import com.goose.tapp.NFCDetails.Portrait;
import com.goose.tapp.NFCDetails.Settings;
import com.goose.tapp.NFCDetails.StudyECE452;
import com.goose.tapp.NFCDetails.Volume;
import com.goose.tapp.NFCDetails.Wifi;

public class NFCDetailsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    public static FirebaseUser user;
    private NFCDetails nfcDetails;
    private DatabaseReference databaseReference;

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

        // Set up checkbox
        CheckBox browserOpener =  findViewById (R.id.browserOpener);
        CheckBox wifiOpener = findViewById(R.id.wifiOpener);
        CheckBox studyECE452 = findViewById(R.id.studyECE452);
        CheckBox bluetoothOpener = findViewById(R.id.bluetoothOpener);
        CheckBox volumeEditor = findViewById(R.id.volumeEditor);
        CheckBox applicationopener = findViewById(R.id.applicationopener);
        CheckBox brightness = findViewById(R.id.brightness);
        CheckBox potraitMode = findViewById(R.id.potraitMode);

        // Get the nfcDetails to populate activity
        nfcDetails = (NFCDetails)getIntent().getSerializableExtra("NFC_DETAILS");
        if (nfcDetails == null ) {
            Log.e("NFCDetailsActivity", "Failed to get NFC Details - NFC_DETAILS is empty");
            return;
        }
        else if (nfcDetails.getSettings() == null) {
            nfcDetails.setSettings( new Settings());
        } else {
            browserOpener.setChecked(nfcDetails.getSettings().getBrowser() != null);
            wifiOpener.setChecked(nfcDetails.getSettings().getWifi() != null);
            studyECE452.setChecked(nfcDetails.getSettings().getStudyECE452() != null);
            bluetoothOpener.setChecked(nfcDetails.getSettings().getBluetooth() != null);
            volumeEditor.setChecked(nfcDetails.getSettings().getVolume() != null);
            applicationopener.setChecked(nfcDetails.getSettings().getApplication() != null);
            brightness.setChecked(nfcDetails.getSettings().getBrightness() != null);
            potraitMode.setChecked(nfcDetails.getSettings().getPortrait() != null);
        }

        // Populate activity with nfcDetails
        nfcTitle.setText(nfcDetails.getTagLocation());

        databaseReference = FirebaseDatabase.getInstance().getReference();

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
        final CheckBox checkbox = ((CheckBox) view);
        Boolean checked = checkbox.isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.browserOpener:
                if (checked) {
                    TextModal object = new TextModal(NFCDetailsActivity.this, "Browser Link", "ex: google.ca", "");
                    object.setTextModalListener(new TextModal.TextModalListener() {
                        @Override
                        public void modalResponse(String text) {
                            Browser browser = new Browser();
                            browser.setLink(text);
                            nfcDetails.getSettings().setBrowser(browser);
                        }

                        @Override
                        public void modalCancel() {
                            checkbox.setChecked(false);
                        }
                    });
                } else {
                    nfcDetails.getSettings().setBrowser(null);
                }
                break;
        }
    }

    public void onCheckboxClicked2(View view) {
        // Is the view now checked?
        final CheckBox checkbox = ((CheckBox) view);
        Boolean checked = checkbox.isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.wifiOpener:
                if (checked) {
                    CheckBoxModal object = new CheckBoxModal(NFCDetailsActivity.this, "Wifi", "Enable Wifi", false);
                    object.setCheckBoxModalListener(new CheckBoxModal.CheckBoxModalListener() {
                        @Override
                        public void modalResponse( Boolean status){
                            Wifi wifi = new Wifi();
                            wifi.setStatus(status);
                            nfcDetails.getSettings().setWifi(wifi);
                        }

                        @Override
                        public void modalCancel() {
                            checkbox.setChecked(false);
                        }
                    });
                } else {
                    nfcDetails.getSettings().setWifi(null);
                }
                break;
        }
    }

    public void onCheckboxClicked3(View view) {
        // Is the view now checked?
        final CheckBox checkbox = ((CheckBox) view);
        Boolean checked = checkbox.isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.studyECE452:
                if (checked) {
                    CheckBoxModal object = new CheckBoxModal(NFCDetailsActivity.this, "Pop Quiz!", "Enable ECE452 Question", false);
                    object.setCheckBoxModalListener(new CheckBoxModal.CheckBoxModalListener() {
                        @Override
                        public void modalResponse( Boolean status){
                            StudyECE452 studyECE452 = new StudyECE452();
                            studyECE452.setStatus(status);
                            nfcDetails.getSettings().setStudyECE452(studyECE452);
                        }

                        @Override
                        public void modalCancel() {
                            checkbox.setChecked(false);
                        }
                    });
                } else {
                    nfcDetails.getSettings().setStudyECE452(null);
                }
                break;
        }
    }

    public void onCheckboxClicked4(View view) {
        // Is the view now checked?
        final CheckBox checkbox = ((CheckBox) view);
        Boolean checked = checkbox.isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.bluetoothOpener:
                if (checked) {
                    CheckBoxModal object = new CheckBoxModal(NFCDetailsActivity.this, "Bluetooth", "Enable Bluetooth", false);
                    object.setCheckBoxModalListener(new CheckBoxModal.CheckBoxModalListener() {
                        @Override
                        public void modalResponse( Boolean status){
                            Bluetooth bluetooth = new Bluetooth();
                            bluetooth.setStatus(status);
                            nfcDetails.getSettings().setBluetooth(bluetooth);
                        }

                        @Override
                        public void modalCancel() {
                            checkbox.setChecked(false);
                        }
                    });
                } else {
                    nfcDetails.getSettings().setBluetooth(null);
                }
                break;
        }
    }

    public void onCheckboxClicked5(View view) {
        // Is the view now checked?
        final CheckBox checkbox = ((CheckBox) view);
        Boolean checked = checkbox.isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.volumeEditor:
                if (checked) {
                    CheckBoxModal object = new CheckBoxModal(NFCDetailsActivity.this, "Volume", "Volume Mute", false);
                    object.setCheckBoxModalListener(new CheckBoxModal.CheckBoxModalListener() {
                        @Override
                        public void modalResponse( Boolean status){
                            Volume volume = new Volume();
                            volume.setMute(status);
                            nfcDetails.getSettings().setVolume(volume);
                        }

                        @Override
                        public void modalCancel() {
                            checkbox.setChecked(false);
                        }
                    });
                } else {
                    nfcDetails.getSettings().setVolume(null);
                }
                break;
        }
    }

    public void onCheckboxClicked6(View view) {
        // Is the view now checked?
        final CheckBox checkbox = ((CheckBox) view);
        Boolean checked = checkbox.isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.applicationopener:
                if (checked) {
                    AppsModal object = new AppsModal(NFCDetailsActivity.this, "Select app to open");
                    object.setAppsModalListener(new AppsModal.AppsModalListener() {
                        @Override
                        public void modalResponse( String  appName, String packageName){
                            Application application = new Application();
                            application.setAppName(appName);
                            application.setAppPackage(packageName);
                            nfcDetails.getSettings().setApplication(application);
                        }

                        @Override
                        public void modalCancel() {
                            checkbox.setChecked(false);
                        }
                    });
                } else {
                    nfcDetails.getSettings().setApplication(null);
                }
                break;
        }
    }

    public void onCheckboxClicked7(View view) {
        // Is the view now checked?
        final CheckBox checkbox = ((CheckBox) view);
        Boolean checked = checkbox.isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.brightness:
                if (checked) {
                    SeekbarModal object = new SeekbarModal(NFCDetailsActivity.this, "Brightness", 0);
                    object.setSeekbarModalListener(new SeekbarModal.SeekbarModalListener() {
                        @Override
                        public void modalResponse( int value){
                            Brightness brightness = new Brightness();
                            brightness.setValue(value);
                            nfcDetails.getSettings().setBrightness(brightness);
                        }

                        @Override
                        public void modalCancel() {
                            checkbox.setChecked(false);
                        }
                    });
                } else {
                    nfcDetails.getSettings().setBrightness(null);
                }
                break;
        }
    }

    public void onCheckboxClicked8(View view) {
        // Is the view now checked?
        final CheckBox checkbox = ((CheckBox) view);
        Boolean checked = checkbox.isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.potraitMode:
                if (checked) {
                    CheckBoxModal object = new CheckBoxModal(NFCDetailsActivity.this, "Portrait", "Portrait Mode Enabled", false);
                    object.setCheckBoxModalListener(new CheckBoxModal.CheckBoxModalListener() {
                        @Override
                        public void modalResponse( Boolean status){
                            Portrait portrait = new Portrait();
                            portrait.setStatus(status);
                            nfcDetails.getSettings().setPortrait(portrait);
                        }

                        @Override
                        public void modalCancel() {
                            checkbox.setChecked(false);
                        }
                    });
                } else {
                    nfcDetails.getSettings().setPortrait(null);
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        updateFirebase();

    }

    public void updateFirebase() {
        databaseReference.child("NFCIds").child(nfcDetails.getNfcID()).child("settings").setValue(nfcDetails.getSettings());
    }
}