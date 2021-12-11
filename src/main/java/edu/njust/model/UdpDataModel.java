package edu.njust.model;

import java.sql.Timestamp;

public class UdpDataModel {
    private Timestamp timestamp;
    private String origin;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
