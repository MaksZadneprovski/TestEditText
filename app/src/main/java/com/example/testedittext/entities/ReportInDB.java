package com.example.testedittext.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.util.Date;

@Entity
public class ReportInDB {
    @PrimaryKey
    @NonNull
    private String name;

    private long dateOfCreate;

    private String report;

    public ReportInDB(ReportEntity report) {
        this.report = new Gson().toJson(report);
        this.name = report.getName();
        if (report.getDate() == null || report.getDate().isEmpty()){
            this.dateOfCreate = new Date().getTime();
        }

    }

    public ReportInDB() {
    }

    public ReportEntity getReportEntity(){
        ReportEntity report = new Gson().fromJson(this.report, ReportEntity.class);
        if (report.getDate() == null || report.getDate().isEmpty()){
            //report.setDate(this.dateOfCreate);
        }
        return  report;
    }

    @Override
    public String toString() {
        return "ReportInDB{" +
                "name='" + name + '\'' +
                ", dateOfCreate=" + dateOfCreate +
                ", report='" + report + '\'' +
                '}';
    }

    public String getNameIgnoreCase() {
        return name.toLowerCase();
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public long getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(long dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
