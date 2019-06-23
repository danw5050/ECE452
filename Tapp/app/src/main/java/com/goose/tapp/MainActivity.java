package com.goose.tapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mTextView = (TextView) findViewById(R.id.title);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });

/*
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!mNfcAdapter.isEnabled()) {
            mTextView.setText("NFC is disabled.");
        } else {
            mTextView.setText("Everything works with NFC!");
        }
*/
        //Intent myIntent = new Intent(MainActivity.this, NFCTriggerActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        //MainActivity.this.startActivity(myIntent);
    }

    protected void login(){
        Intent myIntent = new Intent(MainActivity.this, NFCListActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

}
