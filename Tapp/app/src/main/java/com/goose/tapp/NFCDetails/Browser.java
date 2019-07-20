package com.goose.tapp.NFCDetails;

import java.io.Serializable;

public class Browser implements Serializable {
    private String link;

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
