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
import java.util.Arrays;
import java.util.Map;

public class F0Report {
    static final float number_of_characters_per_line = 60.0F;

    public static Workbook generateF0(Workbook wb, ReportEntity report, Map<String, String> param, Context context ){

        ArrayList<String> apparats = new ArrayList<>(Arrays.asList("Рубильник", "От шин"));

        Sheet sheetF0 = wb.getSheet("F0");

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


        Row row = sheetF0.createRow(6);
        Cell cell = row.createCell(0);
        cell.setCellValue("ПРОТОКОЛ № " + Excel.numberF0Protocol + " Проверки согласования параметров цепи «фаза – нуль» с характеристиками аппаратов  защиты");
        cell.setCellStyle(styleTitle);

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetF0, 7,number_of_characters_per_line, report, wb);
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

                        if (!group.getAddress().isEmpty() && !apparats.contains(group.defenseApparatus) && !group.getAddress().toLowerCase().trim().equals("резерв")) {

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
                            String releaseType = group.getReleaseType();
                            String apparat = group.getDefenseApparatus();
                            if (releaseType == null || releaseType.isEmpty()){
                                if (apparat.equals("Предохранитель")){
                                    cell.setCellValue("ОВВ");
                                }
                                if (apparat.equals("Автомат") || apparat.equals("Автомат + УЗО") || apparat.equals("Дифавтомат")){
                                    cell.setCellValue("ОВВ; МД");
                                }
                            }
                            else {
                                cell.setCellValue(releaseType);
                            }
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


        countRow = Excel.printInstruments(context, sheetF0, countRow, style5, TypeOfWork.PhaseZero.toString());


        countRow += 2;
        row = sheetF0.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Заключение:");
        cell.setCellStyle(style4);

        row = sheetF0.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Сопротивление петли «фаза-нуль» соответствует требованиям п. 6.4.3.7.3 ГОСТ Р 50571.16—2019; п. 411.4.4 ГОСТ Р 50571.3— 2009; СТО 34.01-23.1-001-2017,");
        cell.setCellStyle(style5);

        row = sheetF0.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        за исключением пунктов указанных в п/п ______");
        cell.setCellStyle(style5);


        countRow += 2;
        // Заполняем Фамилии, Должности и т.д.
        countRow = fillRekvizity(countRow, sheetF0, wb, param, 1,6,12);

        // Получаем количество страниц (Значение неточное, может быть посчитано неточно)
        int countRowInList = 90;
        Storage.pagesCountF0 = (int) Math.ceil((double) countRow / countRowInList);

        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetF0), // индекс листа
                0, // начало столбца
                15, // конец столбца
                0, //начать строку
                countRow// конец строки
        );

        return wb;
    }
}
