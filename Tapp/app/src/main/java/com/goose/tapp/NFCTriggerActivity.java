package com.goose.tapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class NFCTriggerActivity extends AppCompatActivity {


    TextView NFCActionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_trigger);
        NFCActionTextView = findViewById(R.id.NFCActionTextView);
        readFromIntent(getIntent());

    }

    @Override
    protected void onResume() {
        super.onResume();

        // {This functionality is temporary for demo purposes}
/*
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // open up the browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://learn.uwaterloo.ca/"));
                startActivity(browserIntent);
            }
        }, 2000);
        */

    }

    private void readFromIntent(Intent intent) {

        // Verify that the NFC tag is the intent
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            // Get raw text of NFC
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                NdefMessage msg = (NdefMessage) rawMsgs[0];

                String text = "";
                byte[] payload = msg.getRecords()[0].getPayload();
                String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
                int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"

                try {
                    text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
                    NFCActionTextView.setText(text);
                } catch (UnsupportedEncodingException e) {
                    NFCActionTextView.setText(getApplicationContext().getString(R.string.nfc_unable_parse));
                }
            } else {
                // No contents within the NFC Tag
                NFCActionTextView.setText(getApplicationContext().getString(R.string.nfc_unable_read));
            }
        }
    }
}
