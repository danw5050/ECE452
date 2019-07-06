package com.goose.tapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class NFCWriteActivity extends AppCompatActivity {

    TextView writingStatus;

    private NdefMessage nfcId = null;
    NFCManager nfcManager;
    Tag currentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_write);
        writingStatus = findViewById(R.id.writingStatus);

        nfcManager = new NFCManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Create the nfcId to write to NFC
        nfcId =  nfcManager.createTextMessage("asdfasdf!");

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
