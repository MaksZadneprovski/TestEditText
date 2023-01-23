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

    public static String getRangeForF0(int countRow){
        return "CONCATENATE(E"+ (countRow+1) + "*5,\" - \",E" + (countRow+1) + "*10)";
    }

    public static String getRangeForAvtomat(int countRow){
        return "CONCATENATE(E"+ (countRow+1) + "*5,\" - \",E" + (countRow+1) + "*10)";
    }

    public static String getNeOtklTokUzo(int countRow){
        return "G"+ (countRow+1) + "/2";
    }

    public static String getRandomDifCurrent(int countRow){
        return "CEILING(RANDBETWEEN( H"+ (countRow+1) +  ", G" + (countRow+1) +"),1)";
    }

    public static String getRandomDifTime(){
        return "CEILING(RANDBETWEEN(22,43),1)";
    }

    public static String getUstavkaPeregruz(int countRow){
        return "E"+ (countRow+1) + "* 1.45";
    }

    public static String getIspTokPeregruz(int countRow){
        return "E"+ (countRow+1) + "* 2.55";
    }

    public static String getRandomAvtomatPeregruzTime(){
        return "CEILING(RANDBETWEEN(5,45),1)";
    }

    public static String getAvtomatTokNeSrab(int countRow){
        return "E"+ (countRow+1) + "* 5";
    }

    public static String getAvtomatTokSrab(int countRow){
        return "E"+ (countRow+1) + "* 10";
    }

}
