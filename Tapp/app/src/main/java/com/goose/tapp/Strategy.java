package com.goose.tapp;

import android.content.Context;

// This is the Strategy interface.
public interface Strategy {
    public void doTheSpecifiedTask(Context context, String link, boolean wifiOn);
}
