package com.goose.tapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

public class openBrowser extends AppCompatActivity {
    // Constructor that accepts a link to open a browser
    public openBrowser(Context context, String link) {
        // Add https otherwise the code fails to run
        if (!link.startsWith("http://") && !link.startsWith("https://")){
            link = "https://" + link;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }
}
