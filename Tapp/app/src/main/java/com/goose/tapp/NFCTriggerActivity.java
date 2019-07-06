package com.goose.tapp;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class NFCTriggerActivity extends AppCompatActivity {


    TextView NFCActionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the activity
        setContentView(R.layout.activity_nfc_trigger);
        NFCActionTextView = findViewById(R.id.NFCActionTextView);

        // Get the text from NFC Tag
        nfcFromIntent(getIntent());

    }

    /*
     * nfcFromIntent verifies that activity started from NFC and extracts the content of NFC Tag
     */
    private void nfcFromIntent(Intent intent) {

        String nfcTriggerMessage = "";

        // Verify that the NFC tag is the intent
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            // Get raw text of NFC
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {

                // Get text from NdefMessage
                NdefMessage msg = (NdefMessage) rawMsgs[0];
                try {
                    nfcTriggerMessage = ndefToString(msg);

                    /*
                     * TODO: Add a function here that uses NFC Tag ID to launch required functions
                     */

                } catch (UnsupportedEncodingException e) {
                    nfcTriggerMessage = getApplicationContext().getString(R.string.nfc_unable_parse);
                }

            } else {
                // No contents within the NFC Tag
                nfcTriggerMessage = getApplicationContext().getString(R.string.nfc_unable_read);
            }
        }

        NFCActionTextView.setText(nfcTriggerMessage);
    }

    /*
     * NdefToString used to get payload string from NdefMessage
     */
    public String ndefToString(NdefMessage msg) throws UnsupportedEncodingException {
        byte[] payload = msg.getRecords()[0].getPayload();

        // Set language and encoding options
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;

        // Return parsed string
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }
}
