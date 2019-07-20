package com.goose.tapp.NFCDetails;

import java.io.Serializable;

public class Brightness implements Serializable {
    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
