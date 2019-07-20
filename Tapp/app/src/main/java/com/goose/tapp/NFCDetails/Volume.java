package com.goose.tapp.NFCDetails;

import java.io.Serializable;

public class Volume implements Serializable {
    private boolean mute;

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public boolean getMute() {
        return mute;
    }
}
