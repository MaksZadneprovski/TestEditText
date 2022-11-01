package com.example.testedittext.utils;

import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    // Текущий отчет
    public static ReportEntity currentReportEntityStorage;


    public static int currentNumberSelectedShield;
    // Для копирования


    public static boolean isDeleteShield;


    public static void setGroupList(ArrayList<Group> groups) {
        Shield shield = currentReportEntityStorage.getShields().get(currentNumberSelectedShield);
        shield.setShieldGroups(groups);
        currentReportEntityStorage.getShields().remove(currentNumberSelectedShield);
        currentReportEntityStorage.getShields().add(currentNumberSelectedShield, shield);
    }
}
