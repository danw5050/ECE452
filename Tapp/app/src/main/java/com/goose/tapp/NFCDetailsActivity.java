package com.goose.tapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        showQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show QR Code for NFC Tag
                Intent myIntent = new Intent(NFCDetailsActivity.this, QRCodeGenerationActivity.class);
                myIntent.putExtra("EXTRA_QR_STRING", nfcDetails.getNfcID());
                NFCDetailsActivity.this.startActivity(myIntent);
            }
        });
    }

}