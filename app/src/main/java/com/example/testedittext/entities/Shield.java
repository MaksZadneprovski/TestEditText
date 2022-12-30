package com.example.testedittext.entities;

import com.example.testedittext.entities.enums.Phases;

import java.io.Serializable;
import java.util.ArrayList;

// Класс, представляющий электрический щит
public class Shield implements Serializable {
    private String name;
    private boolean isPEN;
    private Phases phases;
    private ArrayList<MetallicBond> metallicBonds;
    private ArrayList<Group> shieldGroups;
    private ArrayList<Defect> defects;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPEN() {
        return isPEN;
    }

    public void setPEN(boolean PEN) {
        isPEN = PEN;
    }

    public Phases getPhases() {
        return phases;
    }

    public void setPhases(Phases phases) {
        this.phases = phases;
    }

    public ArrayList<MetallicBond> getMetallicBonds() {
        return metallicBonds;
    }

    public void setMetallicBonds(ArrayList<MetallicBond> metallicBonds) {
        this.metallicBonds = metallicBonds;
    }

    public ArrayList<Group> getShieldGroups() {
        return shieldGroups;
    }

    public void setShieldGroups(ArrayList<Group> shieldGroups) {
        this.shieldGroups = shieldGroups;
    }

    public ArrayList<Defect> getDefects() {
        return defects;
    }

    public void setDefects(ArrayList<Defect> defects) {
        this.defects = defects;
    }

    @Override
    public String toString() {
        return "Shield{" +
                "name='" + name + '\'' +
                ", isPEN=" + isPEN +
                ", phases=" + phases +
                ", metallicBonds=" + metallicBonds +
                ", shieldGroups=" + shieldGroups +
                ", defects=" + defects +
                '}';
    }
}
