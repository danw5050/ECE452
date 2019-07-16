package com.goose.tapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;

public class openBrowser extends AppCompatActivity implements Strategy{
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        String link = (String) settings.get("open_browser");
        if(link == null || link.length() == 0 || link.isEmpty()){
            return;
        }
        // Add https otherwise the code fails to run
        if (!link.startsWith("http://") && !link.startsWith("https://")){
            link = "https://" + link;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }
}