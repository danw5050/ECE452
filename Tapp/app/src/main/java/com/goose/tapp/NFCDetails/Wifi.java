package com.goose.tapp.NFCDetails;

import java.io.Serializable;

public class Wifi implements Serializable {
    private boolean status;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
