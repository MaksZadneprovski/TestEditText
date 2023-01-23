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

public class F0Report {

    public static Workbook generateF0(Workbook wb, ReportEntity report){

        Sheet sheetF0 = wb.getSheet("F0");

        // Create a new font and alter it.
        Font font8 = wb.createFont();
        font8.setFontHeightInPoints((short)8);
        font8.setFontName("Times New Roman");
        font8.setBold(false);

        Font font12 = wb.createFont();
        font12.setFontHeightInPoints((short)16);
        font12.setFontName("Times New Roman");
        font12.setBold(true);

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

        CellStyle styleTitle;
        styleTitle = wb.createCellStyle();
        styleTitle.setWrapText(true);
        styleTitle.setFont(font12);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Стиль для рамки в конце таблицы
        CellStyle styleEndTable;
        styleEndTable = wb.createCellStyle();
        styleEndTable.setBorderLeft(BorderStyle.THIN);
        styleEndTable.setLeftBorderColor(IndexedColors.BLACK.getIndex());


        Row row = sheetF0.createRow(6);
        Cell cell = row.createCell(0);
        cell.setCellValue("ПРОТОКОЛ № " + ExcelData.numberF0Protocol + " Проверки согласования параметров цепи «фаза – нуль» с характеристиками аппаратов  защиты");
        cell.setCellStyle(styleTitle);

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();



        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetF0, 14, report, wb);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Заполняем строку погоды
        Report.fillWeather(sheetF0, 10,  report, wb);

        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
        int countRow = 28;
        int paragraph = 1;
        String avtomat = "QF";
        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);
                row = sheetF0.createRow(countRow);

                // Для удаления строки с названием щита, если там не оказалось измерений
                boolean presenceOfF0 = false;

                // Объединяем столбцы для вставки названия щита
                sheetF0.addMergedRegion(new CellRangeAddress(
                        countRow, //first row (0-based)
                        countRow, //last row  (0-based)
                        0, //first column (0-based)
                        14  //last column  (0-based)
                ));

                // Вставляем название щита
                cell = row.createCell(0);
                ////////////////////////////////////////////////////////////////////////
                cell.setCellValue(shield.getName());
                cell.setCellStyle(style);

                cell = row.createCell(15);
                cell.setCellValue("");
                cell.setCellStyle(styleEndTable);

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

                            row = sheetF0.createRow(countRow);

                            // Строки с измерениями есть, значит название щита тоже есть в отчете
                            presenceOfF0 = true;

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

                            // Типовое обозначение автомата
                            cell = row.createCell(2);
                            cell.setCellValue(group.getMachineBrand());
                            cell.setCellStyle(style);

                            // Столбец Тип расцепителя
                            cell = row.createCell(3);
                            cell.setCellValue(group.getReleaseType());
                            cell.setCellStyle(style);

                            // Столбец Ном.ток
                            cell = row.createCell(4);
                            cell.setCellValue(group.getRatedCurrent());
                            cell.setCellStyle(style);



                            // Столбец Диапазон тока срабатывания расцепителя
                            cell = row.createCell(5);
                            cell.setCellFormula(ExcelFormula.getRangeForF0(countRow));
                            cell.setCellStyle(style);

                            // Столбцы измерений

                            // Создаем столбцы измерений и ставим туда "-"
                            for (int k = 6; k < 15; k++) {
                                cell = row.createCell(k);
                                cell.setCellValue("-");
                                cell.setCellStyle(style);
                            }

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

                            // Если в изоляции везде прочерки, "соотв." не заполнится
                            boolean conformity = false;

                            switch (group.getPhases()) {
                                case "А":
                                    cell = row.createCell(9);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(style);

                                    cell = row.createCell(6);
                                    cell.setCellFormula(ExcelFormula.getComputeResistA(countRow));
                                    cell.setCellStyle(style);

                                    conformity = true;
                                    break;
                                case "В":
                                    cell = row.createCell(10);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(style);

                                    cell = row.createCell(7);
                                    cell.setCellFormula(ExcelFormula.getComputeResistB(countRow));
                                    cell.setCellStyle(style);

                                    conformity = true;
                                    break;
                                case "С":
                                    cell = row.createCell(11);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(style);

                                    cell = row.createCell(8);
                                    cell.setCellFormula(ExcelFormula.getComputeResistC(countRow));
                                    cell.setCellStyle(style);

                                    conformity = true;
                                    break;
                                case "АВС":
                                    cell = row.createCell(9);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(style);

                                    cell = row.createCell(6);
                                    cell.setCellFormula(ExcelFormula.getComputeResistA(countRow));
                                    cell.setCellStyle(style);

                                    cell = row.createCell(10);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(style);

                                    cell = row.createCell(7);
                                    cell.setCellFormula(ExcelFormula.getComputeResistB(countRow));
                                    cell.setCellStyle(style);

                                    cell = row.createCell(11);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(style);

                                    cell = row.createCell(8);
                                    cell.setCellFormula(ExcelFormula.getComputeResistC(countRow));
                                    cell.setCellStyle(style);

                                    conformity = true;
                                    break;
                            }

                            // Столбец доп время срабатывания. аппарата защиты
                            cell = row.createCell(12);
                            cell.setCellValue("0,4");
                            cell.setCellStyle(style);

                            // Столбец  время срабатывания. аппарата защиты по времятоковой
                            cell = row.createCell(13);
                            cell.setCellValue("< 0,4");
                            cell.setCellStyle(style);

                            // Столбец соотв
                            if (conformity) row.getCell(14).setCellValue("соотв.");

                            // Столбец  после таблицы
                            cell = row.createCell(15);
                            cell.setCellValue("");
                            cell.setCellStyle(styleEndTable);


                            countRow++;
                        }
                    }
                }

                if (!presenceOfF0) {
                    sheetF0.removeMergedRegion(sheetF0.getNumMergedRegions() - 1);
                    sheetF0.createRow(countRow);
                    row.createCell(0);
                    cell.setCellValue("");
                    // Так как в этом стиле нет рамок
                    cell.setCellStyle(styleTitle);
                    countRow--;
                }
            }
        }


        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetF0), // индекс листа
                0, // начало столбца
                14, // конец столбца
                0, //начать строку
                countRow - 1 // конец строки
        );

        return wb;
    }
}
