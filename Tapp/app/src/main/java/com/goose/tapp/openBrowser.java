package com.goose.tapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class openBrowser extends AppCompatActivity {
    // Constructor that accepts a link to open a browser
    public openBrowser(final String link) {
        Log.d("Link", link);

        // Actually open the link in the browser
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // open up the browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        }, 2000);
    }
}
