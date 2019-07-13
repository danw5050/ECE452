package com.goose.tapp;

import android.content.Context;

public class ContextObject {
    private Strategy strategy;

    public ContextObject(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(Context context, String link, boolean wifiOn){
        strategy.doTheSpecifiedTask(context, link, wifiOn);
    }
}
