package com.example.testedittext.entities;

// В щите есть группы, это класс, представляющий их
public class Group {

    private String name;

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
