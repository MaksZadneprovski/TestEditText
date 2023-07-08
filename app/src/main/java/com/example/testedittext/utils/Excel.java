package com.example.testedittext.utils;

import android.content.Context;

import com.example.testedittext.activities.report_list.ReportListActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.InstrumentDAO;
import com.example.testedittext.entities.InstrumentInDB;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public class Excel {
    public static int numberInsulationProtocol = 0;
    public static int numberF0Protocol = 0;
    public static int numberMSProtocol = 0;
    public static int numberVOProtocol = 0;
    public static int numberUzoProtocol = 0;
    public static int numberGroundingProtocol = 0;
    public static int numberAvtomatProtocol = 0;


    public  static int printInstruments(Context context, Sheet sheet, int countRow, CellStyle style, String typeOfReport ){
        InstrumentDAO instrumentDAO = Bd.getAppDatabaseClass(context).getInstrumentDao();
        List<InstrumentInDB> instrumentList =instrumentDAO.getAllInstruments();

        if (instrumentList != null && !instrumentList.isEmpty()) {
            int instrumentCount = 1;
            for (InstrumentInDB instrument : instrumentList) {
                if (instrument.getTypesOfReports() != null && !instrument.getTypesOfReports().isEmpty() && instrument.getTypesOfReports().contains(typeOfReport)) {
                    Row row = sheet.createRow(++countRow);
                    Cell cell = row.createCell(1);
                    cell.setCellValue("Проверки проведены приборами:");
                    cell.setCellStyle(style);
                    countRow++;

                    row = sheet.createRow(++countRow);
                    cell = row.createCell(1);
                    cell.setCellValue(instrumentCount+" :    Тип -  " + instrument.getType() + ";");
                    cell.setCellStyle(style);

                    row = sheet.createRow(++countRow);
                    cell = row.createCell(1);
                    cell.setCellValue("        Заводской номер - " + instrument.getZavnomer() + ";");
                    cell.setCellStyle(style);

                    row = sheet.createRow(++countRow);
                    cell = row.createCell(1);
                    cell.setCellValue("        Диапазон измерения - " + instrument.getRange() + ";");
                    cell.setCellStyle(style);

                    row = sheet.createRow(++countRow);
                    cell = row.createCell(1);
                    cell.setCellValue("        Класс точности - " + instrument.getTochnost() + ";");
                    cell.setCellStyle(style);

                    row = sheet.createRow(++countRow);
                    cell = row.createCell(1);
                    cell.setCellValue("        Дата поверки : последняя - " + instrument.getLastdate() + ", очередная - " + instrument.getNextdate() + ";");
                    cell.setCellStyle(style);

                    row = sheet.createRow(++countRow);
                    cell = row.createCell(1);
                    cell.setCellValue("        № аттестата (св-ва) - " + instrument.getAttestat() + ";");
                    cell.setCellStyle(style);

                    row = sheet.createRow(++countRow);
                    cell = row.createCell(1);
                    cell.setCellValue("        Орган гос. метрологической службы, проводивший поверку - " + instrument.getOrgan() + ".");
                    cell.setCellStyle(style);
                }
                instrumentCount += 1;
            }
        }


        return countRow;
    }



}
