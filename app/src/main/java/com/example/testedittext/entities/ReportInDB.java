package com.example.testedittext.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

@Entity
public class ReportInDB {
    @PrimaryKey
    @NonNull
    private String path;

    private String report;

    public ReportInDB(ReportEntity report) {
        this.report = new Gson().toJson(report);
        this.path = report.getPath();
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
