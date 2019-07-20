package com.goose.tapp.NFCDetails;

import java.io.Serializable;

public class Settings implements Serializable {
    private Application application;
    private Bluetooth bluetooth;
    private Brightness brightness;
    private Browser browser;
    private Portrait portrait;
    private com.goose.tapp.NFCDetails.studyECE452 studyECE452;
    private Volume volume;
    private Wifi wifi;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Bluetooth getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(Bluetooth bluetooth) {
        this.bluetooth = bluetooth;
    }

    public Brightness getBrightness() {
        return brightness;
    }

    public void setBrightness(Brightness brightness) {
        this.brightness = brightness;
    }

    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public Portrait getPortrait() {
        return portrait;
    }

    public void setPortrait(Portrait portrait) {
        this.portrait = portrait;
    }

    public com.goose.tapp.NFCDetails.studyECE452 getStudyECE452() {
        return studyECE452;
    }

    public void setStudyECE452(com.goose.tapp.NFCDetails.studyECE452 studyECE452) {
        this.studyECE452 = studyECE452;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Wifi getWifi() {
        return wifi;
    }

    public void setWifi(Wifi wifi) {
        this.wifi = wifi;
    }
}
