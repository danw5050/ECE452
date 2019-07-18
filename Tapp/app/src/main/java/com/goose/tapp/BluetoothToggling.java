package com.goose.tapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;

public class BluetoothToggling extends AppCompatActivity implements Strategy {
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        Boolean status = false;
        int REQUEST_ENABLE_BT = 1;

        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("bluetooth")){
                String [] separated = entry.getValue().toString().split("=");
                status = Boolean.parseBoolean(separated[1].substring(0, separated[1].length() - 1));
            }
        }

        if(status){
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                // Device does not support Bluetooth, show toast message
                return;
            }
            // Enable bluetooth
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(enableBtIntent);
            }
        }
    }
}
