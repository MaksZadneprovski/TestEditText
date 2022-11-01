package com.example.testedittext.entities;

// В щите есть группы, это класс, представляющий их
public class Group {

    private String designation;
    private String address;

    public Group() {

    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address != null ? address : "";
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
