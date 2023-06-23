package com.example.testedittext.utils;

import com.example.testedittext.entities.Efficiency;
import com.example.testedittext.entities.Ground;
import com.example.testedittext.entities.GroundingDevice;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.MetallicBond;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;

import java.util.ArrayList;

public class Calculator {

    public static Efficiency getEfficiency(ReportEntity report){
        int shieldsSize = 0;
        int countLine = 0;
        int metsvyaz = 0;
        int zazeml = 0;

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
        efficiency.setReportName(report.getName());
        efficiency.setShieldsSize(shieldsSize);
        efficiency.setCountLine(countLine);
        efficiency.setMetsvyaz(metsvyaz);
        efficiency.setZazeml(zazeml);

        return efficiency;
    }
}
