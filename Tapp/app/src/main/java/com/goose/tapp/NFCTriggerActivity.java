package com.goose.tapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class NFCTriggerActivity extends AppCompatActivity {


    TextView NFCActionTextView;

    static Context openContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the activityNFCActionTextView
        setContentView(R.layout.activity_nfc_trigger);
        // NFCActionTextView = findViewById(R.id.NFCActionTextView);

        openContext = getApplicationContext();
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
                    NFCActionTextView.setText(nfcTriggerMessage);
                }

            } else {
                // No contents within the NFC Tag
                nfcTriggerMessage = getApplicationContext().getString(R.string.nfc_unable_read);
                NFCActionTextView.setText(nfcTriggerMessage);
            }
        }
    }

    private void workWithNFCId(String nfcTriggerMessage){
        // Get the NFC ID from the client.
        String nfcId = nfcTriggerMessage;

        // Read data from the database in Firebase
        FirebaseDatabase.getInstance().getReference()
                .child("NFCIds")
                .child(nfcId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // NFC ID doesn't exist itself.
                        if(dataSnapshot == null || dataSnapshot.getValue() == null){
                            Toast.makeText(openContext,"This NFC has not been registered with our systems yet.",Toast.LENGTH_SHORT).show();

                            Intent myIntent = new Intent(openContext, NewNFCActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            openContext.startActivity(myIntent);

                            finish();
                            return;
                        }

                        Map<String, Object> settings = (Map<String, Object>) dataSnapshot.child("settings").getValue();

                        // No settings object exists.
                        if(settings == null){
                            Toast.makeText(openContext,"Add some tasks to this NFC!",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }

                        ContextObject contextObject = new ContextObject(new wifiToggling());
                        contextObject.executeStrategy(openContext, settings);

                        contextObject = new ContextObject(new openBrowser());
                        contextObject.executeStrategy(openContext, settings);

                        contextObject = new ContextObject(new SetVolumeLevel());
                        contextObject.executeStrategy(openContext, settings);

                        contextObject = new ContextObject(new BluetoothToggling());
                        contextObject.executeStrategy(openContext, settings);

                        contextObject = new ContextObject(new OpenExternalApplication());
                        contextObject.executeStrategy(openContext, settings);

                        contextObject = new ContextObject(new PortraitModeToggling());
                        contextObject.executeStrategy(openContext, settings);

                        contextObject = new ContextObject(new ScreenBrightness());
                        contextObject.executeStrategy(openContext, settings);

                        contextObject = new ContextObject(new StudyFeature());
                        contextObject.executeStrategy(openContext, settings);

                        finish();
                        return;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        AlertDialog alertDialog = new AlertDialog.Builder(NFCTriggerActivity.this).create();
                        alertDialog.setTitle("Unable to launch. Please log into the application first.");
                        alertDialog.setMessage(databaseError.getDetails());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
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
