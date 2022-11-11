package com.example.testedittext.report_creator;

import android.content.Context;

import com.example.testedittext.R;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Report {

    Context context;
    private  String fileName;
    ReportEntity report;

    public Report(Context context, String fileName, ReportEntity report) {
        this.context = context;
        this.fileName = fileName;
        this.report = report;
    }




    public void generate() throws IOException {

        Workbook wb = null;

        wb = WorkbookFactory.create(context.getResources().openRawResource(R.raw.report));

        Sheet sheet = wb.getSheet("l2");

        // Создаем стиль для создания рамки у ячейки
        CellStyle style = wb.getCellStyleAt(0);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

////////////////////////////////////////////////////////////////////////////////////////////////////
        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();
        // Начинаем с 3 строки, первые 2 занимает шапка таблицы
        int countRow = 2;

        Row row;
        String avtomat = "QF";
        System.out.println(report);
        // Проход по щитам
        for (int i = 0; i < shields.size(); i++) {
            Shield shield = shields.get(i);
            row = sheet.createRow(countRow);

            Cell cell;

            for (int k = 0; k < 16; k++) {
                cell = row.createCell(k);
                cell.setCellValue(" ");
                cell.setCellStyle(style);
            }

            // Вставляем название щита
            cell = row.createCell(1);
            ////////////////////////////////////////////////////////////////////////
            System.out.println(shield.getName());
            cell.setCellValue(shield.getName());
            cell.setCellStyle(style);

            countRow++;
            // Получаем группы щита
            ArrayList<Group> shieldGroups = shield.getShieldGroups();
            // Переменная для автоматической генерации номера автомата (QF1, QF2...)
            int avtomatCount = 1;
            if (shieldGroups != null) {
                // Проход по группам
                for (int j = 0; j < shieldGroups.size(); j++) {
                    // Получаем группу и записываем ее данные в таблицу, если имеется поле адрес
                    Group group = shieldGroups.get(j);
                    ////////////////////////////////////////////////////////////////////////
                    System.out.println(j);

                    if (!group.getAddress().isEmpty()) {

                        row = sheet.createRow(countRow);

                        // Столбец пункт
                        cell = row.createCell(0);
                        cell.setCellValue(String.valueOf(j + 1));
                        cell.setCellStyle(style);

                        // Столбец наименование линии
                        cell = row.createCell(1);
                        String lineName = "";
                        if (group.getDesignation().isEmpty()) {
                            lineName = avtomat + avtomatCount + " - " + group.getAddress();
                            avtomatCount++;
                        }
                        else lineName = group.getDesignation() + " - " + group.getAddress();
                        cell.setCellValue(lineName);
                        cell.setCellStyle(style);

                        // Столбец Марка, кол-во жил, сечение
                        cell = row.createCell(2);
                        String val = group.getCable() + " " + group.getNumberOfWireCores() + "x" + group.getWireThickness();
                        cell.setCellValue(val);
                        cell.setCellStyle(style);

                        // Столбец Напряжение мегаомметра
                        cell = row.createCell(3);
                        cell.setCellValue("2500");
                        cell.setCellStyle(style);

                        // Столбец Допустимое R
                        cell = row.createCell(4);
                        cell.setCellValue("0,5");
                        cell.setCellStyle(style);

                        // Столбец

                        // Столбец

                        countRow++;
                    }
                }
            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////



        //try (OutputStream fileOut = new FileOutputStream("w.xls")) {
        //try (OutputStream fileOut = context.getAssets().("workbook2.xls")) {

        try (FileOutputStream fileOut = new FileOutputStream(getExternalPath())) {
            wb.write(fileOut);
        }

        wb.close();
    }

    private File getExternalPath() {
        return new File(context.getExternalFilesDir(null), fileName);
    }

}
