package com.example.testedittext.entities;

// Металлосвязь. У щита может быть несколько измерений (РЕ контакт розеток, дверь щита, корпус щита)
public class MetallicBond {

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
}
