package com.example.testedittext.entities;

import java.io.Serializable;

// Металлосвязь. У щита может быть несколько измерений (РЕ контакт розеток, дверь щита, корпус щита)
public class MetallicBond implements Serializable, Cloneable {

    private String peContact;
    private String countElements;
    private boolean noPe;

    public String getPeContact() {
        return peContact;
    }

    public void setPeContact(String peContact) {
        this.peContact = peContact;
    }

    public String getCountElements() {
        return countElements;
    }

    public void setCountElements(String countElements) {
        this.countElements = countElements;
    }

    public boolean isNoPe() {
        return noPe;
    }

    public void setNoPe(boolean noPe) {
        this.noPe = noPe;
    }

    @Override
    public MetallicBond clone() {
        try {
            MetallicBond clone = (MetallicBond) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
