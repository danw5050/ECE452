package com.goose.tapp;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class NFCTriggerActivity extends AppCompatActivity {


    TextView NFCActionTextView;
    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the activity
        setContentView(R.layout.activity_nfc_trigger);
        // NFCActionTextView = findViewById(R.id.NFCActionTextView);

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
                    workWithNFCId(nfcTriggerMessage);

                } catch (UnsupportedEncodingException e) {
                    nfcTriggerMessage = getApplicationContext().getString(R.string.nfc_unable_parse);
                    // NFCActionTextView.setText(nfcTriggerMessage);
                }

            } else {
                // No contents within the NFC Tag
                nfcTriggerMessage = getApplicationContext().getString(R.string.nfc_unable_read);
                // NFCActionTextView.setText(nfcTriggerMessage);
            }
        }
    }

    private void workWithNFCId(String nfcTriggerMessage){
        // Get the NFC ID from the client.
        String nfcId = nfcTriggerMessage;

        // Read data from the database
        FirebaseDatabase.getInstance().getReference()
                .child("NFCIds")
                .child((nfcId))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> settings = (Map<String, Object>) dataSnapshot.getValue();

                        ContextObject contextObject1 = new ContextObject(new wifiToggling());
                        contextObject1.executeStrategy(getApplicationContext(), settings);

                        ContextObject contextObject2 = new ContextObject(new openBrowser());
                        contextObject2.executeStrategy(getApplicationContext(), settings);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        /*Do Nothing*/
                    }
                });
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
