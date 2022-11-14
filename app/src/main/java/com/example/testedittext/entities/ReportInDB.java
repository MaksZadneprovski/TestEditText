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
        this.dateOfCreate = new Date().getTime();
    }

    public ReportInDB() {
    }

    public ReportEntity getReportEntity(){
        return new Gson().fromJson(report, ReportEntity.class);
    }

    @Override
    public String toString() {
        return "ReportInDB{" +
                "report='" + report + '\'' +
                '}';
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
