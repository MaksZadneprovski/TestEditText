package com.example.testedittext.report_creator;

import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.ExcelData;
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
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;

public class InsulationReport {


    public static Workbook generateInsulation(Workbook wb, ReportEntity report){

        Sheet sheetInsulation = wb.getSheet("Insulation");

        // Create a new font and alter it.
        Font font8 = wb.createFont();
        font8.setFontHeightInPoints((short)8);
        font8.setFontName("Times New Roman");
        font8.setBold(false);

        CellStyle style;
        // Создаем стиль для создания рамки у ячейки
        style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        //style.setBorderRight(BorderStyle.THIN);
        //style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font8);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetInsulation, 15, report, wb );

        // Заполняем строку погоды
        Report.fillWeather(sheetInsulation, 13,  report, wb);

        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
        int countRow = 27;
        int paragraph = 1;

        Row row;
        String avtomat = "QF";
        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);
                row = sheetInsulation.createRow(countRow);

                Cell cell;

                // Объединяем столбцы для вставки названия щита
                sheetInsulation.addMergedRegion(new CellRangeAddress(
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

                    // Для автоматического заполнения фаз, если не указана конкретная
                    int numberPhase = 1;

                    // Проход по группам
                    for (int j = 0; j < shieldGroups.size(); j++) {
                        // Получаем группу и записываем ее данные в таблицу, если имеется поле адрес
                        Group group = shieldGroups.get(j);
                        ////////////////////////////////////////////////////////////////////////

                        if (!group.getAddress().isEmpty()) {

                            row = sheetInsulation.createRow(countRow);

                            //увеличиваем высоту строки, чтобы вместить две строки текста
                            //row.setHeightInPoints((2*sheetInsulation.getDefaultRowHeightInPoints()));

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

                            // Столбцы измерений

                            // Автоматическое заполнение А,В,С, елси фаза не заполнена
                            if (group.getPhases().isEmpty() && !group.getAddress().isEmpty()){
                                switch (numberPhase){
                                    case 1: group.setPhases("А"); break;
                                    case 2: group.setPhases("В"); break;
                                    case 3: group.setPhases("С"); break;
                                }
                                if (numberPhase == 3) numberPhase = 1;
                                else numberPhase++;
                            }

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
                                    setInsulation1Phase(row, isPE,8,style);
                                    conformity = true;
                                    break;
                                case "В":
                                    setInsulation1Phase(row, isPE,9,style);
                                    conformity = true;
                                    break;
                                case "С":
                                    setInsulation1Phase(row, isPE,10,style);
                                    conformity = true;
                                    break;
                                case "АВС":
                                    setInsulation3Phase(row, isPE,style);
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

        //устанавливаем область печати
        wb.setPrintArea(
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ExcelData.indexInsulationSheet, // индекс листа
                0, // начало столбца
                15, // конец столбца
                0, //начало строки
                countRow - 1 // конец строки
        );

        return wb;
    }

    public static void setInsulation1Phase(Row row, boolean isPE, int i, CellStyle style){

        Cell cell = row.createCell(i);
        cell.setCellFormula(ExcelFormula.randomInsulation);
        cell.setCellStyle(style);

        if (isPE) {
            cell = row.createCell(i+3);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(style);

            cell = row.createCell(14);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(style);

        }
    }

    public static void setInsulation3Phase(Row row, boolean isPE, CellStyle style){
        for (int i = 5; i < 8; i++) {
            Cell cell = row.createCell(i);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(style);

            cell = row.createCell(i+3);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(style);
            if (isPE) {
                cell = row.createCell(i+6);
                cell.setCellFormula(ExcelFormula.randomInsulation);
                cell.setCellStyle(style);
            }
        }
        if (isPE) {
            Cell cell = row.createCell(14);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(style);
        }
    }
}
