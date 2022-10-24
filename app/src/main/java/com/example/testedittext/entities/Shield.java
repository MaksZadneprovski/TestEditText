package com.example.testedittext.entities;

import com.example.testedittext.entities.enums.Phases;

import java.util.ArrayList;

// Класс, представляющий электрический щит
public class Shield {
    private String name;
    private boolean isPEN;
    private Phases phases;
    private ArrayList<MetallicBond> metallicBonds;
    private ArrayList<ShieldGroups> shieldGroups;


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

    public ArrayList<ShieldGroups> getShieldGroups() {
        return shieldGroups;
    }

    public void setShieldGroups(ArrayList<ShieldGroups> shieldGroups) {
        this.shieldGroups = shieldGroups;
    }
}
