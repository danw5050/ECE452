package com.goose.tapp;

import android.content.Context;

import java.util.Map;

public class ContextObject {
    private Strategy strategy;

    public ContextObject(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(Context context,  Map<String, Object> settings){
        strategy.doTheSpecifiedTask(context, settings);
    }
}