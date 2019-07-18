package com.goose.tapp;

import android.content.Context;
import android.media.AudioManager;
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
            }
        }

        AudioManager am;
        am= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        // Reference: https://stackoverflow.com/questions/11699603/is-it-possible-to-turn-off-the-silent-mode-programmatically-in-android
        if(mute){
            //For Silent mode
            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        else{
            //For Normal mode
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }
}
