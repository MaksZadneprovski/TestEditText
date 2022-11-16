package com.example.testedittext.report_creator;

import android.content.Context;

import com.example.testedittext.R;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.ExcelFormula;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;


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

        Workbook wb;

        wb = WorkbookFactory.create(context.getResources().openRawResource(R.raw.report2));

        Sheet sheet = wb.getSheet("l2");

        // Create a new font and alter it.
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)8);
        font.setFontName("Times New Roman");
        font.setBold(false);

        // Создаем стиль для создания рамки у ячейки
        CellStyle style = wb.getCellStyleAt(1);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        //style.setBorderRight(BorderStyle.THIN);
        //style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

////////////////////////////////////////////////////////////////////////////////////////////////////
        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();
        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
        int countRow = 27;
        int paragraph = 1;

        Row row;
        String avtomat = "QF";
        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                Shield shield = shields.get(i);
                row = sheet.createRow(countRow);

                Cell cell;



                sheet.addMergedRegion(new CellRangeAddress(
                        countRow, //first row (0-based)
                        countRow, //last row  (0-based)
                        0, //first column (0-based)
                        15  //last column  (0-based)
                ));

                // Вставляем название щита
                cell = row.createCell(0);
                ////////////////////////////////////////////////////////////////////////
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

                        if (!group.getAddress().isEmpty()) {

                            row = sheet.createRow(countRow);

                            //увеличиваем высоту строки, чтобы вместить две строки текста
                            row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));

                            // Столбец пункт
                            cell = row.createCell(0);
                            cell.setCellValue(paragraph++);
                            cell.setCellStyle(style);

                            // Столбец наименование линии
                            cell = row.createCell(1);
                            String lineName = "";
                            if (group.getDesignation().isEmpty()) {
                                lineName = avtomat + avtomatCount + " - " + group.getAddress();
                                avtomatCount++;
                            } else lineName = group.getDesignation() + " - " + group.getAddress();
                            cell.setCellValue(lineName);
                            cell.setCellStyle(style);

                            // Столбец Марка, кол-во жил, сечение
                            cell = row.createCell(2);
                            if (!group.getNumberOfWireCores().isEmpty()) {
                                String val = group.getCable() + " " + group.getNumberOfWireCores() + "x" + group.getWireThickness();
                                cell.setCellValue(val);
                            }

                            cell.setCellStyle(style);

                            // Столбец Напряжение мегаомметра
                            cell = row.createCell(3);
                            cell.setCellValue("2500");
                            cell.setCellStyle(style);

                            // Столбец Допустимое R
                            cell = row.createCell(4);
                            cell.setCellValue("0,5");
                            cell.setCellStyle(style);

                            for (int k = 5; k < 16; k++) {
                                cell = row.createCell(k);
                                cell.setCellValue("-");
                                cell.setCellStyle(style);
                            }


                            // Нужно ли заполнять N-PE
                            boolean isPE = group.getNumberOfWireCores().equals("3") || group.getNumberOfWireCores().equals("5");
                            // Если в изоляции везде прочерки, "соотв." не заполнится
                            boolean conformity = false;

                            // Столбцы сопротивления
                            switch (group.getPhases()) {
                                case "А":
                                    setInsulation1Phase(row, isPE,8);
                                    conformity = true;
                                    break;
                                case "В":
                                    setInsulation1Phase(row, isPE,9);
                                    conformity = true;
                                    break;
                                case "С":
                                    setInsulation1Phase(row, isPE,10);
                                    conformity = true;
                                    break;
                                case "АВС":
                                    setInsulation3Phase(row, isPE);
                                    conformity = true;
                                    break;
                            }

                            // Столбец соотв
                            if (conformity) row.getCell(15).setCellValue("соотв.");



                            countRow++;
                        }
                    }
                }
            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////

        try (FileOutputStream fileOut = new FileOutputStream(getExternalPath())) {
            wb.write(fileOut);
        }

        wb.close();
    }

    private void setInsulation1Phase(Row row, boolean isPE, int i){
        row.getCell(i).setCellFormula(ExcelFormula.random);
        if (isPE) {
            row.getCell(i+3).setCellFormula(ExcelFormula.random);
            row.getCell(14).setCellFormula(ExcelFormula.random);
        }
    }

    private void setInsulation3Phase(Row row, boolean isPE){
        for (int i = 5; i < 11; i++) {
            row.getCell(i).setCellFormula(ExcelFormula.random);
            row.getCell(i+3).setCellFormula(ExcelFormula.random);
            if (isPE) {
                row.getCell(i+6).setCellFormula(ExcelFormula.random);
            }
        }
        if (isPE) {
            row.getCell(14).setCellFormula(ExcelFormula.random);
        }
    }

    private File getExternalPath() {
        return new File(context.getExternalFilesDir(null), fileName);
    }

}
