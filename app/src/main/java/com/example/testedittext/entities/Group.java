package com.example.testedittext.entities;

import androidx.annotation.NonNull;

import com.example.testedittext.entities.enums.Phases;

import java.io.Serializable;
import java.util.Map;

// В щите есть группы, это класс, представляющий их
public class Group implements Serializable, Cloneable {

    public String designation;
    public String address;
    public String phases;
    public String cable;
    public String numberOfWireCores;
    public String wireThickness;
    public String defenseApparatus;
    public String machineBrand;
    public String ratedCurrent;
    public String releaseType;
    public String f0Range;
    public String tSrabAvt;
    public String markaUzo;
    public String iNomUzo;
    public String iDifNom;
    public String typeDifCurrent;
    public boolean isSetMeasure = false;
    public Map<String, String> insulation;
    public Map<String, String> f0;

    public Group() {

    }

    public String getTypeDifCurrent() {
        return typeDifCurrent;
    }

    public void setTypeDifCurrent(String typeDifCurrent) {
        this.typeDifCurrent = typeDifCurrent;
    }

    public String getPhases() {
        return phases;
    }

    public void setPhases(String phases) {
        this.phases = phases;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCable() {
        return cable;
    }

    public void setCable(String cable) {
        this.cable = cable;
    }

    public String getNumberOfWireCores() {
        return numberOfWireCores;
    }

    public void setNumberOfWireCores(String numberOfWireCores) {
        this.numberOfWireCores = numberOfWireCores;
    }

    public String getWireThickness() {
        return wireThickness;
    }

    public void setWireThickness(String wireThickness) {
        this.wireThickness = wireThickness;
    }

    public String getDefenseApparatus() {
        return defenseApparatus;
    }

    public void setDefenseApparatus(String defenseApparatus) {
        this.defenseApparatus = defenseApparatus;
    }

    public String getMachineBrand() {
        return machineBrand;
    }

    public void setMachineBrand(String machineBrand) {
        this.machineBrand = machineBrand;
    }

    public String getRatedCurrent() {
        return ratedCurrent;
    }

    public void setRatedCurrent(String ratedCurrent) {
        this.ratedCurrent = ratedCurrent;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    public String getF0Range() {
        return f0Range;
    }

    public void setF0Range(String f0Range) {
        this.f0Range = f0Range;
    }

    public String gettSrabAvt() {
        return tSrabAvt;
    }

    public void settSrabAvt(String tSrabAvt) {
        this.tSrabAvt = tSrabAvt;
    }

    public String getMarkaUzo() {
        return markaUzo;
    }

    public void setMarkaUzo(String markaUzo) {
        this.markaUzo = markaUzo;
    }

    public String getiNomUzo() {
        return iNomUzo;
    }

    public void setiNomUzo(String iNomUzo) {
        this.iNomUzo = iNomUzo;
    }

    public String getiDifNom() {
        return iDifNom;
    }

    public void setiDifNom(String iDifNom) {
        this.iDifNom = iDifNom;
    }

    public boolean isSetMeasure() {
        return isSetMeasure;
    }

    public void setSetMeasure(boolean setMeasure) {
        isSetMeasure = setMeasure;
    }

    public Map<String, String> getInsulation() {
        return insulation;
    }

    public void setInsulation(Map<String, String> insulation) {
        this.insulation = insulation;
    }

    public Map<String, String> getF0() {
        return f0;
    }

    public void setF0(Map<String, String> f0) {
        this.f0 = f0;
    }

    @Override
    public String toString() {
        return "Group{" +
                "designation='" + designation + '\'' +
                ", address='" + address + '\'' +
                ", phases='" + phases + '\'' +
                ", cable='" + cable + '\'' +
                ", numberOfWireCores='" + numberOfWireCores + '\'' +
                ", wireThickness='" + wireThickness + '\'' +
                ", defenseApparatus='" + defenseApparatus + '\'' +
                ", machineBrand='" + machineBrand + '\'' +
                ", ratedCurrent='" + ratedCurrent + '\'' +
                ", releaseType='" + releaseType + '\'' +
                ", f0Range='" + f0Range + '\'' +
                ", tSrabAvt='" + tSrabAvt + '\'' +
                ", markaUzo='" + markaUzo + '\'' +
                ", iNomUzo='" + iNomUzo + '\'' +
                ", iDifNom='" + iDifNom + '\'' +
                ", isSetMeasure=" + isSetMeasure +
                ", insulation=" + insulation +
                ", f0=" + f0 +
                '}';
    }

    @NonNull
    @Override
    public Group clone() {
        try {
            Group clone = (Group) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
