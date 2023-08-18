package com.example.testedittext.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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

        // Если даты, поставленной вручную нет, ставим сегодняшнюю
        if (report.getDate() == null || report.getDate().isEmpty()){
            this.dateOfCreate = new Date().getTime();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
            LocalDate date = LocalDate.parse(report.getDate(), formatter);
            long milliseconds = date.atStartOfDay().toInstant(ZoneOffset.of("+03:00")).toEpochMilli();
            this.dateOfCreate = milliseconds;
        }

    }

    public ReportInDB() {
    }

    public ReportEntity getReportEntity(){
        ReportEntity report = new Gson().fromJson(this.report, ReportEntity.class);
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
