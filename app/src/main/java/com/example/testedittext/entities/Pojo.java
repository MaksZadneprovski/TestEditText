package com.example.testedittext.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pojo implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("report")
    @Expose
    private String report;


    public Pojo(String name, String report) {
        super();
        this.name = name;
        this.report = report;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getReport() {
        return report;
    }
    public void setReport(String report) {
        this.report = report;
    }


}
