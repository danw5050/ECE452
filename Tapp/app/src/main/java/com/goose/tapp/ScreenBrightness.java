package com.goose.tapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;

public class ScreenBrightness  extends AppCompatActivity implements Strategy {
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {

        int value = 0;
        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("brightness")){
                String [] separated = entry.getValue().toString().split("=");
                value = Integer.parseInt(separated[1].substring(0, separated[1].length() - 1));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.System.canWrite(context)) {
                        ContentResolver cResolver = context.getContentResolver();
                        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, value);

                    } else {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            }
        }
    }
}
