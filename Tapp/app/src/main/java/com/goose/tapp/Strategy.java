package com.goose.tapp;

import android.content.Context;

import java.util.Map;

// This is the Strategy interface.
public interface Strategy {
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings);
}