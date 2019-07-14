package com.goose.tapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class NFCWriteActivity extends AppCompatActivity {

    /*
     * TODO: CALL NFCWriteActivity through this approach:
     *    Intent myIntent = new Intent(MainActivity.this, NFCWriteActivity.class);
     *    myIntent.putExtra("EXTRA_NFC_STRING", "{Id of NFC you want to write}");
     *   MainActivity.this.startActivity(myIntent);
     */

    TextView writingStatus;

    private NdefMessage nfcId = null;
    NFCManager nfcManager;
    Tag currentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_write);
        writingStatus = findViewById(R.id.writingStatus);

        // Get the string from activity launching intent
        String nfcValue = getIntent().getStringExtra("EXTRA_NFC_STRING");
        if (nfcValue == null || nfcValue.isEmpty() ) {
            Log.e("NFCWriteActivity", "Failed to write to NFC - EXTRA_NFC_STRING is empty");
            return;
        }

        // Create the NFCId to write
        nfcManager = new NFCManager(this);
        nfcId = nfcManager.createTextMessage(nfcValue);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Create intent listener to catch NFC tag tap
        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
        IntentFilter[] intentFiltersArray = new IntentFilter[] {};
        String[][] techList = new String[][] {
                { android.nfc.tech.Ndef.class.getName() },
                { android.nfc.tech.NdefFormatable.class.getName() }
        };
        NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
        nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
    }


  /*  @Override
    public void onPause() {
        super.onPause();

        // Disable the intent listener
        nfcManager.disableDispatch();
    }
*/
    /*
     * onNewIntent triggered on NFC Tap
     */
    @Override
    public void onNewIntent(Intent intent) {
        // It is the time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (nfcId != null) {
            writingStatus.setText("NFC Tripped");
            nfcManager.writeTag(currentTag, nfcId);
        }
        else {
            writingStatus.setText("NFC Message is not valid");
        }
    }
}
