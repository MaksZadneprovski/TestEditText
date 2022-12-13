package com.example.testedittext.utils;

public class ExcelFormula {
    public static String randomInsulation = "RANDBETWEEN(200,999)";
    public static String randomMS = "RANDBETWEEN(1,4)/100";

    public static String getRandomCurrent(String curr){
        if (curr.isEmpty()) return "0";
        int currInt = Integer.parseInt(curr);
        return "CEILING(RANDBETWEEN(" + currInt*13 + "," + currInt*15 + "),1)";
    }

    public static String getComputeResistA(int countRow){

        return "ROUND(230/J" + (countRow+1) + ",2)";
    }
    public static String getComputeResistB(int countRow){
        return "ROUND(230/K" + (countRow+1) + ",2)";
    }
    public static String getComputeResistC(int countRow){
        return "ROUND(230/L" + (countRow+1) + ",2)";
    }

    public static String getRange(int countRow){
        return "CONCATENATE(E"+ (countRow+1) + "*5,\" - \",E" + (countRow+1) + "*10)";
    }
}
