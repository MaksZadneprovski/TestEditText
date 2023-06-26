package com.example.testedittext.utils;

import android.content.Context;

import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.MetallicBond;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Storage {
    // Текущий отчет
    public static ReportEntity currentReportEntityStorage;
    public static Map <String, List<Map<String, String>>> defects;

    public static int currentNumberSelectedShield;
    public static int currentNumberOfPressedDefect;


    public static boolean isDeleteShield;
    public static boolean isDeleteDefect;
    public static boolean waitLoad = true;

    public static int pagesCountVO = 0;
    public static int pagesCountMS = 0;
    public static int pagesCountInsulation = 0;
    public static int pagesCountF0 = 0;
    public static int pagesCountUzo = 0;
    public static int pagesCountAvtomat = 0;
    public static int pagesCountGround = 0;
    public static int pagesCountTotal = 3;

    public Context context;

    public Storage(Context context) {
        this.context = context;

    }

    public void parseDefects(){
        try {
            defects = DefectsParser.parseDefectsFromFile(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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


    static {

    }

}
