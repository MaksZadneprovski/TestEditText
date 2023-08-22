package com.example.testedittext.report_creator;

import static com.example.testedittext.report_creator.Report.fillRekvizity;

import android.content.Context;

import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.entities.enums.TypeOfWork;
import com.example.testedittext.utils.Excel;
import com.example.testedittext.utils.ExcelFormula;
import com.example.testedittext.utils.Storage;

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
import java.util.Map;

public class InsulationReport {
    static final float number_of_characters_per_line = 65.0F;

    public static Workbook generateInsulation(Workbook wb, ReportEntity report, Map<String, String> param, Context context){

        Sheet sheetInsulation = wb.getSheet("Insulation");

        // Create a new font and alter it.
        Font font14 = wb.createFont();
        font14.setFontHeightInPoints((short)14);
        font14.setFontName("Times New Roman");

        Font font16 = wb.createFont();
        font16.setFontHeightInPoints((short)16);
        font16.setFontName("Times New Roman");
        font16.setBold(true);


        CellStyle style;
        // Создаем стиль для создания рамки у ячейки
        style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle styleTitle;
        // Создаем стиль для создания рамки у ячейки
        styleTitle = wb.createCellStyle();
        styleTitle.setWrapText(true);
        styleTitle.setFont(font16);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Стиль для рамки в конце таблицы
        CellStyle styleEndTable;
        styleEndTable = wb.createCellStyle();
        styleEndTable.setBorderLeft(BorderStyle.THIN);
        styleEndTable.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        Row row = sheetInsulation.createRow(9);
        Cell cell = row.createCell(0);
        cell.setCellValue("ПРОТОКОЛ № " + Excel.numberInsulationProtocol + " Проверки сопротивления изоляции");
        cell.setCellStyle(styleTitle);

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetInsulation, 7, number_of_characters_per_line,
                report, wb );

        // Заполняем строку погоды
        Report.fillWeather(sheetInsulation, 13,  report, wb);

        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
        int countRow = 27;
        int paragraph = 1;

        String avtomat = "QF";
        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);
                row = sheetInsulation.createRow(countRow);

                // Для удаления строки с названием щита, если там не оказалось измерений
                boolean presenceOfInsulation = false;

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

                cell = row.createCell(16);
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

                            row = sheetInsulation.createRow(countRow);

                            // Строки с измерениями есть, значит название щита тоже есть в отчете
                            presenceOfInsulation = true;

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
                            try {
                                String wireThicknessString = group.getWireThickness();
                                if (wireThicknessString.isEmpty()) wireThicknessString = "4";
                                wireThicknessString = wireThicknessString.replace(",",".");
                                float wireThickness = Float.parseFloat(wireThicknessString);
                                if (wireThickness >= 16.0) {
                                    cell.setCellValue("2500");
                                } else {
                                    cell.setCellValue("1000");
                                }
                            } catch (NumberFormatException e) {
                                // Обработка ошибки парсинга
                                // Например, установка значения по умолчанию или логирование ошибки
                                cell.setCellValue("1000"); // или другое значение по умолчанию
                                e.printStackTrace(); // или логирование ошибки
                            }

                            cell.setCellStyle(style);

                            // Столбец Допустимое R
                            cell = row.createCell(4);
                            cell.setCellValue("0,5");
                            cell.setCellStyle(style);

                            // Столбцы измерений

                            // Автоматическое заполнение А,В,С, елси фаза не заполнена
                            if (group.getPhases().isEmpty()){
                                switch (numberPhase){
                                    case 1: group.setPhases("А"); break;
                                    case 2: group.setPhases("В"); break;
                                    case 3: group.setPhases("С"); break;
                                }
                                if (numberPhase == 3) numberPhase = 1;
                                else numberPhase++;
                            }

                            // Заполняем все ячейки прочерками
                            for (int k = 5; k < 16; k++) {
                                cell = row.createCell(k);
                                cell.setCellValue("-");
                                cell.setCellStyle(style);
                            }

                            // Нужно ли заполнять N-PE
                            boolean isPE;

                            // Если в изоляции везде прочерки, "соотв." не заполнится
                            boolean conformity = false;

                            // Столбцы сопротивления

                            if (!group.getAddress().contains("Резерв") && !group.getAddress().contains("резерв")){
                                switch (group.getPhases()) {
                                    case "А":
                                        // Если 3 жилы, значит есть РЕ
                                        isPE = group.getNumberOfWireCores().equals("3");
                                        //  Заполняем формулами генерации числа нужные ячейки
                                        setInsulation1Phase(row, isPE,8,style);
                                        // Для того чтобы в столбце соотв. написалось
                                        conformity = true;
                                        break;
                                    case "В":
                                        isPE = group.getNumberOfWireCores().equals("3");
                                        setInsulation1Phase(row, isPE,9,style);
                                        conformity = true;
                                        break;
                                    case "С":
                                        isPE = group.getNumberOfWireCores().equals("3");
                                        setInsulation1Phase(row, isPE,10,style);
                                        conformity = true;
                                        break;
                                    case "АВС":
                                        // Если 5 жил, значит есть РЕ
                                        isPE = group.getNumberOfWireCores().equals("5");
                                        setInsulation3Phase(row, isPE,style);
                                        conformity = true;
                                        break;
                                }
                            }

                            // Столбец соотв
                            if (conformity) row.getCell(15).setCellValue("соотв.");

                            // Столбец после таблицы
                            cell = row.createCell(16);
                            cell.setCellStyle(styleEndTable);


                            countRow++;
                        }
                    }
                }

                if (!presenceOfInsulation) {
                    sheetInsulation.removeMergedRegion(sheetInsulation.getNumMergedRegions() - 1);
                    sheetInsulation.createRow(countRow);
                    row.createCell(0);
                    cell.setCellValue("");
                    // Так как в этом стиле нет рамок
                    cell.setCellStyle(styleTitle);
                    countRow--;
                }
            }
        }

        // Приборы и закалючение
        Font font14Bold = wb.createFont();
        font14Bold.setFontHeightInPoints((short)14);
        font14Bold.setFontName("Times New Roman");
        font14Bold.setBold(true);

        CellStyle style4;
        style4 = wb.createCellStyle();
        style4.setAlignment(HorizontalAlignment.LEFT);
        style4.setFont(font14Bold);

        CellStyle style5;
        style5 = wb.createCellStyle();
        style5.setAlignment(HorizontalAlignment.LEFT);
        style5.setFont(font14);


        countRow = Excel.printInstruments(context, sheetInsulation, countRow, style5, TypeOfWork.Insulation.toString());


        countRow += 2;
        row = sheetInsulation.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Заключение:");
        cell.setCellStyle(style4);

        row = sheetInsulation.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Сопротивление изоляции проводов и кабелей соответствует требованиям п. 6.4.3.3 ГОСТ Р 50571.16—2019, табл. 6.1; СТО 34.01-23.1-001-2017,");
        cell.setCellStyle(style5);

        row = sheetInsulation.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        за исключением пунктов указанных в п/п ______");
        cell.setCellStyle(style5);

        countRow += 2;
        // Заполняем Фамилии, Должности и т.д.
        countRow = fillRekvizity(countRow, sheetInsulation, wb, param, 1,7,12);

        // Получаем количество страниц (Значение неточное, может быть посчитано неточно)
        int countRowInList = 89;
        Storage.pagesCountInsulation = (int) Math.ceil((double) countRow / countRowInList);

        //устанавливаем область печати
        wb.setPrintArea(
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                wb.getSheetIndex(sheetInsulation), // индекс листа
                0, // начало столбца
                16, // конец столбца
                    0, //начало строки
                countRow// конец строки
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
