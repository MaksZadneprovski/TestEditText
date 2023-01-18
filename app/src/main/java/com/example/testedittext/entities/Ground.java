package com.example.testedittext.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Ground implements Serializable {

    private ArrayList<GroundingDevice> groundingDevices;
    private String soilType;
    private String soilCharacter;
    private String voltage;
    private String neutralMode;
    private String lightningProtectionCategory;
    private String seazonKoef;
    private String udelnoeR;

    public ArrayList<GroundingDevice> getGroundingDevices() {
        return groundingDevices;
    }

    public void setGroundingDevices(ArrayList<GroundingDevice> groundingDevices) {
        this.groundingDevices = groundingDevices;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getSoilCharacter() {
        return soilCharacter;
    }

    public void setSoilCharacter(String soilCharacter) {
        this.soilCharacter = soilCharacter;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getNeutralMode() {
        return neutralMode;
    }

    public void setNeutralMode(String neutralMode) {
        this.neutralMode = neutralMode;
    }

    public String getLightningProtectionCategory() {
        return lightningProtectionCategory;
    }

    public void setLightningProtectionCategory(String lightningProtectionCategory) {
        this.lightningProtectionCategory = lightningProtectionCategory;
    }

    public String getSeazonKoef() {
        return seazonKoef;
    }

    public void setSeazonKoef(String seazonKoef) {
        this.seazonKoef = seazonKoef;
    }

    public String getUdelnoeR() {
        return udelnoeR;
    }

    public void setUdelnoeR(String udelnoeR) {
        this.udelnoeR = udelnoeR;
    }
}
