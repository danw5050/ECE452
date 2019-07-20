package com.goose.tapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;

public class SetVolumeLevel extends AppCompatActivity implements Strategy {
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        Boolean mute = false;
        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("volume")){
                String [] separated = entry.getValue().toString().split("=");
                mute = Boolean.parseBoolean(separated[1].substring(0, separated[1].length() - 1));

                AudioManager am;
                am= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

                // Reference: https://stackoverflow.com/questions/11699603/is-it-possible-to-turn-off-the-silent-mode-programmatically-in-android
                if(am != null && notificationManager.isNotificationPolicyAccessGranted()){
                    if(mute){
                        //For Silent mode
                        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        am.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.FLAG_SHOW_UI);
                    }
                    else{
                        //For Normal mode
                        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        am.setStreamVolume(AudioManager.STREAM_RING, 100, AudioManager.FLAG_PLAY_SOUND);
                    }
                }
            }
        }
    }
}
