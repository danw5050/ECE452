package com.goose.tapp.NFCDetails;

import java.io.Serializable;

public class Application implements Serializable {
    private String appPackage;
    private String appName;

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }
}
