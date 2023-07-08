package com.example.testedittext.entities;

import java.io.Serializable;

public class Defect implements Serializable, Cloneable {
    private String defectGroup;
    private String defect;
    private String note;

    public String getDefectGroup() {
        return defectGroup;
    }

    public void setDefectGroup(String defectGroup) {
        this.defectGroup = defectGroup;
    }

    @Override
    public String toString() {
        return "Defect{" +
                "defectGroup='" + defectGroup + '\'' +
                ", defect='" + defect + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getDefect() {
        return defect!=null? defect:"" ;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public Defect clone() {
        try {
            Defect clone = (Defect) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
