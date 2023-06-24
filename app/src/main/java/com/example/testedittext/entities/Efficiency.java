package com.example.testedittext.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Efficiency {
    @SerializedName("shieldsSize")
    private int shieldsSize;
    @SerializedName("countLine")
    private int countLine;
    @SerializedName("metsvyaz")
    private int metsvyaz;
    @SerializedName("zazeml")
    private int zazeml;
    @SerializedName("reportName")
    private String reportName;
    @SerializedName("login")
    private String login;
    @SerializedName("timestamp")
    private String timestamp;

    public Efficiency() {
    }

    public int getShieldsSize() {
        return shieldsSize;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setShieldsSize(int shieldsSize) {
        this.shieldsSize = shieldsSize;
    }

    public int getCountLine() {
        return countLine;
    }

    public void setCountLine(int countLine) {
        this.countLine = countLine;
    }

    public int getMetsvyaz() {
        return metsvyaz;
    }

    public void setMetsvyaz(int metsvyaz) {
        this.metsvyaz = metsvyaz;
    }

    public int getZazeml() {
        return zazeml;
    }

    public void setZazeml(int zazeml) {
        this.zazeml = zazeml;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
