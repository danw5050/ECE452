package com.goose.tapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class NFCDetailsActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the activity views
        setContentView(R.layout.activity_nfc_details);
        TextView nfcTitle = findViewById(R.id.nfcTitle);
        Button showQRCode = findViewById(R.id.showQRCode);
        getSupportActionBar().hide();

        // Get the nfcDetails to populate activity
        final NFCDetails nfcDetails = (NFCDetails)getIntent().getSerializableExtra("NFC_DETAILS");
        if (nfcDetails == null ) {
            Log.e("NFCDetailsActivity", "Failed to get NFC Details - NFC_DETAILS is empty");
            return;
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

     //SAMPLE OF HOW TO USE EACH OF THE CUSTOM MODALS

   /*     AppsModal object3 = new AppsModal(this, "Select Application");
        object3.setAppsModalListener(new AppsModal.AppsModalListener() {
            @Override
            public void modalResponse(String appName, String packageName) {
                Toast toast=Toast.makeText(getApplicationContext(),"a" + appName, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void modalCancel() {

            }
        });


        TextModal object = new TextModal(this, "Title", "hint", "asd");
        object.setTextModalListener(new TextModal.TextModalListener() {
            @Override
            public void modalResponse(String data) {
                Toast toast=Toast.makeText(getApplicationContext(),data, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void modalCancel() {

            }
        });

        CheckBoxModal object2 = new CheckBoxModal(this, "Title", "status" , true);
        object2.setCheckBoxModalListener(new CheckBoxModal.CheckBoxModalListener() {
            @Override
            public void modalResponse(Boolean state) {
                Toast toast=Toast.makeText(getApplicationContext(),"a" + state, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void modalCancel() {

            }
        });



        SeekbarModal object4 = new SeekbarModal(this, "Title", 40);
        object4.setSeekbarModalListener(new SeekbarModal.SeekbarModalListener() {
            @Override
            public void modalResponse(int state) {
                Toast toast=Toast.makeText(getApplicationContext(),"a" + state, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void modalCancel() {

            }
        }); */



        CheckBox UWlearn =  findViewById(R.id.UWlearn);
        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("checkBox1", false);
        UWlearn.setChecked(checked);

        CheckBox silentMode = findViewById(R.id.silentMode);
        boolean checked2 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("checkBox2", false);
        silentMode.setChecked(checked2);

        CheckBox spotifyOpener = findViewById(R.id.spotifyOpener);
        boolean checked3 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("checkBox3", false);
        spotifyOpener.setChecked(checked3);

        CheckBox bluetoothOpener = findViewById(R.id.bluetoothOpener);
        boolean checked4 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("checkBox4", false);
        bluetoothOpener.setChecked(checked4);

        CheckBox clockOpener = findViewById(R.id.clockopener);
        boolean checked5 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("checkBox5", false);
        clockOpener.setChecked(checked5);

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.UWlearn:
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox1", checked).commit();
                break;
        }
    }

    public void onCheckboxClicked2(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.silentMode:
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox2", checked).commit();
                break;
        }
    }

    public void onCheckboxClicked3(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.spotifyOpener:
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox3", checked).commit();
                break;
        }
    }

    public void onCheckboxClicked4(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.bluetoothOpener:
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox4", checked).commit();
                break;
        }
    }

    public void onCheckboxClicked5(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.clockopener:
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean("checkBox5", checked).commit();
                break;
        }
    }
}