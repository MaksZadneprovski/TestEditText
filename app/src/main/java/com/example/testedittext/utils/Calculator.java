package com.example.testedittext.utils;

import com.example.testedittext.entities.Efficiency;
import com.example.testedittext.entities.Ground;
import com.example.testedittext.entities.GroundingDevice;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.MetallicBond;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Calculator {

    public static Efficiency getEfficiency(ReportEntity report){
        int shieldsSize = 0;
        int countLine = 0;
        int metsvyaz = 0;
        int zazeml = 0;
        // Получение текущей даты и времени
        Date currentTime = Calendar.getInstance().getTime();
        // Создание экземпляра SimpleDateFormat с выбранным форматом даты
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

        // Преобразование объекта Date в строку с использованием выбранного формата
        String formattedDate = dateFormat.format(currentTime);

        if (report!=null) {
            ArrayList<Shield> shields = report.getShields();


            if (shields != null) {
                shieldsSize = shields.size();


                Ground ground = report.getGround();
                if (ground != null) {
                    ArrayList<GroundingDevice> groundingDevices = ground.getGroundingDevices();
                    if (groundingDevices != null) {
                        for (GroundingDevice device : groundingDevices) {
                            if (!device.getDestination().isEmpty()) {
                                zazeml += 1;
                            }
                        }
                    }
                }

                for (Shield s : shields) {
                    ArrayList<Group> shieldGroups = s.getShieldGroups();
                    ArrayList<MetallicBond> metallicBonds = s.getMetallicBonds();

                    if (shieldGroups != null) {
                        for (Group g : shieldGroups) {
                            if (!g.getAddress().isEmpty()) {
                                countLine += 1;
                            }
                        }
                    }
                    if (metallicBonds != null) {
                        for (MetallicBond b : metallicBonds) {
                            if (!b.getPeContact().isEmpty()) {
                                metsvyaz += 1;
                            }
                        }
                    }
                }
            }
        }
        Efficiency efficiency = new Efficiency();
        if (report != null) {
            efficiency.setReportName(report.getName());
            efficiency.setShieldsSize(shieldsSize);
            efficiency.setCountLine(countLine);
            efficiency.setMetsvyaz(metsvyaz);
            efficiency.setZazeml(zazeml);
            efficiency.setTimestamp(formattedDate);
        }else{
            efficiency.setReportName(" ");
            efficiency.setShieldsSize(0);
            efficiency.setCountLine(0);
            efficiency.setMetsvyaz(0);
            efficiency.setZazeml(0);
            efficiency.setTimestamp(formattedDate);
        }


        return efficiency;
    }
}
