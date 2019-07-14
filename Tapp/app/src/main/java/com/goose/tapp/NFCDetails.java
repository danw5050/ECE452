package com.goose.tapp;

import java.io.Serializable;

public class NFCDetails implements Serializable {

    private String nfcID;
    private String name;
    private String tagLocation;

    public NFCDetails() { }

    public String getNfcID() {
        return nfcID;
    }

    public void setNfcID(String nfcID) {
        this.nfcID = nfcID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagLocation() {
        return tagLocation;
    }

    public void setTagLocation(String tagLocation) {
        this.tagLocation = tagLocation;
    }
}
