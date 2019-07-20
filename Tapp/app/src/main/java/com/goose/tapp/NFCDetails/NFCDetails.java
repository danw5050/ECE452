package com.goose.tapp.NFCDetails;

import java.io.Serializable;

public class NFCDetails implements Serializable {

    private String nfcID;
    private String name;
    private String tagLocation;
    private Settings settings;

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

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
