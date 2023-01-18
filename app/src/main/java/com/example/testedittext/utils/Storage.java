package com.example.testedittext.utils;

import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.MetallicBond;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    // Текущий отчет
    public static ReportEntity currentReportEntityStorage;

    public static int currentNumberSelectedShield;
    public static int currentNumberOfPressedDefect;


    public static boolean isDeleteShield;
    public static boolean isDeleteDefect;
    public static boolean waitLoad = true;


    public static void setGroupList(ArrayList<Group> groups) {
        Shield shield = currentReportEntityStorage.getShields().get(currentNumberSelectedShield);
        shield.setShieldGroups(groups);
        currentReportEntityStorage.getShields().remove(currentNumberSelectedShield);
        currentReportEntityStorage.getShields().add(currentNumberSelectedShield, shield);
    }

    public static void setMetallicBondList(ArrayList<MetallicBond> metallicBonds) {
        Shield shield = currentReportEntityStorage.getShields().get(currentNumberSelectedShield);
        shield.setMetallicBonds(metallicBonds);
        currentReportEntityStorage.getShields().remove(currentNumberSelectedShield);
        currentReportEntityStorage.getShields().add(currentNumberSelectedShield, shield);
    }

    public static void setDefects(ArrayList<Defect> defects) {
        Shield shield = currentReportEntityStorage.getShields().get(currentNumberSelectedShield);
        shield.setDefects(defects);
        currentReportEntityStorage.getShields().remove(currentNumberSelectedShield);
        currentReportEntityStorage.getShields().add(currentNumberSelectedShield, shield);
    }

}
