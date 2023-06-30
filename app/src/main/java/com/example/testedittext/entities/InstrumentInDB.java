package com.example.testedittext.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.testedittext.db.Converters;

import java.util.ArrayList;

@Entity
@TypeConverters(Converters.class)
public class InstrumentInDB {

    @PrimaryKey
    @NonNull
    private String type;
    private String zavnomer;
    private String range;
    private String tochnost;
    private String lastdate;
    private String nextdate;
    private String attestat;
    private String organ;
    private ArrayList<String> typesOfReports;

    public InstrumentInDB() {}

    public InstrumentInDB(@NonNull String type, String zavnomer, String range, String tochnost, String lastdate, String nextdate, String attestat, String organ,  ArrayList<String> typesOfReports) {
        this.type = type;
        this.zavnomer = zavnomer;
        this.range = range;
        this.tochnost = tochnost;
        this.lastdate = lastdate;
        this.nextdate = nextdate;
        this.attestat = attestat;
        this.organ = organ;
        this.typesOfReports = typesOfReports;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    public String getZavnomer() {
        return zavnomer;
    }

    public ArrayList<String> getTypesOfReports() {
        return typesOfReports;
    }

    public void setTypesOfReports(ArrayList<String> typesOfReports) {
        this.typesOfReports = typesOfReports;
    }

    public void setZavnomer(String zavnomer) {
        this.zavnomer = zavnomer;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getTochnost() {
        return tochnost;
    }

    public void setTochnost(String tochnost) {
        this.tochnost = tochnost;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getNextdate() {
        return nextdate;
    }

    public void setNextdate(String nextdate) {
        this.nextdate = nextdate;
    }

    public String getAttestat() {
        return attestat;
    }

    public void setAttestat(String attestat) {
        this.attestat = attestat;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }
}
