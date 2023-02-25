package com.example.testedittext.report_creator;

import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.MetallicBond;
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
import java.util.Map;

public class UzoReport {

    public static Workbook generateUzo(Workbook wb, ReportEntity report, Map<String, String> param){

        Sheet sheetUzo = wb.getSheet("Uzo");

        // Create a new font and alter it.
        Font font8 = wb.createFont();
        font8.setFontHeightInPoints((short)8);
        font8.setFontName("Times New Roman");
        font8.setBold(false);

        Font font12 = wb.createFont();
        font12.setFontHeightInPoints((short)12);
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
        // Создаем стиль для создания рамки у ячейки
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

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetUzo, 11, report, wb);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Заполняем строку погоды
        Report.fillWeather(sheetUzo, 10,  report, wb);

        Row row = sheetUzo.createRow(7);
        Cell cell = row.createCell(0);
        cell.setCellValue("ПРОТОКОЛ № " + ExcelData.numberUzoProtocol + " Проверки работы устройства защитного отключения (УЗО)");
        cell.setCellStyle(styleTitle);

        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
        int countRow = 21;
        int paragraph = 1;
        String avtomat = "QF";


        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);

                // Для удаления строки с названием щита, если там не оказалось УЗО
                boolean presenceOfUzo = false;

                // Получаем группы щита
                ArrayList<Group> shieldGroups = shield.getShieldGroups();

                row = sheetUzo.createRow(countRow);

                // Объединяем столбцы для вставки названия щита
                sheetUzo.addMergedRegion(new CellRangeAddress(
                        countRow, //first row (0-based)
                        countRow, //last row  (0-based)
                        1, //first column (0-based)
                        10  //last column  (0-based)
                ));

                // Вставляем название щита
                cell = row.createCell(1);
                ////////////////////////////////////////////////////////////////////////
                cell.setCellValue(shield.getName());
                cell.setCellStyle(style);

                cell = row.createCell(11);
                cell.setCellStyle(styleEndTable);

                countRow++;



                // Переменная для автоматической генерации номера автомата (QF1, QF2...)
                int avtomatCount = 1;

                    if (shieldGroups != null) {
                        // Проход по группам
                        for (int j = 0; j < shieldGroups.size(); j++) {
                            Group group = shieldGroups.get(j);
                            String defenseApparatus = group.getDefenseApparatus();
                            if (!group.getAddress().isEmpty() && !defenseApparatus.equals("Автомат") && !defenseApparatus.isEmpty()) {

                                // Строки с узо есть, значит название щита тоже есть в отчете
                                presenceOfUzo = true;

                                row = sheetUzo.createRow(countRow);

                                // Столбец пункт
                                cell = row.createCell(1);
                                cell.setCellValue(paragraph++);
                                cell.setCellStyle(style);

                                // Столбец местоположение и наименование эл.оборудования
                                cell = row.createCell(2);
                                String lineName = "";
                                if (group.getDesignation().isEmpty()) {
                                    lineName = avtomat + avtomatCount + " - " + group.getAddress();
                                    avtomatCount++;
                                } else
                                    lineName = group.getDesignation() + " - " + group.getAddress();

                                cell.setCellValue(lineName);
                                cell.setCellStyle(style);

                            // Столбец Тип УЗО
                            cell = row.createCell(3);
                            if (defenseApparatus.equals("Автомат + УЗО") || defenseApparatus.equals("УЗО")) {
                                cell.setCellValue(group.getMarkaUzo());
                            }else {
                                cell.setCellValue(group.getMachineBrand());
                            }
                            cell.setCellStyle(style);

                            // Столбец Тип диф тока
                            cell = row.createCell(4);
                            cell.setCellValue(group.getTypeDifCurrent());
                            cell.setCellStyle(style);

                                // Столбец Номин. ток нагрузки In, A
                                cell = row.createCell(5);
                                cell.setCellValue(group.getRatedCurrent());
                                cell.setCellStyle(style);



                                // Столбец Номинальн.  отключ. дифф. ток (30)
                                cell = row.createCell(6);
                                cell.setCellValue(group.getiDifNom());
                                cell.setCellStyle(style);
//
                                // Столбец Номинальн. не отключ. дифф. ток (15-30)
                                cell = row.createCell(7);
                                cell.setCellFormula(ExcelFormula.getNeOtklTokUzo(countRow));
                                cell.setCellStyle(style);

                                // Столбец изм. дифф. ток
                                cell = row.createCell(8);
                                cell.setCellFormula(ExcelFormula.getRandomDifCurrent(countRow));
                                cell.setCellStyle(style);

                                // Столбец изм. время
                                cell = row.createCell(9);
                                cell.setCellFormula(ExcelFormula.getRandomDifTime());
                                cell.setCellStyle(style);

                            // Столбец соотв
                            cell = row.createCell(10);
                            cell.setCellValue("соотв.");
                            cell.setCellStyle(style);

                            cell = row.createCell(11);
                            cell.setCellStyle(styleEndTable);

                                countRow++;
                            } else avtomatCount++;
                        }
                    }

                if (!presenceOfUzo) {
                    sheetUzo.removeMergedRegion(sheetUzo.getNumMergedRegions()-1);
                    sheetUzo.createRow(countRow);
                    row.createCell(1);
                    cell.setCellValue("");
                    // Так как в этом стиле нет рамок
                    cell.setCellStyle(styleTitle);
                    countRow--;
                }
            }
        }

        // Приборы и закалючение
        Font font11Bold = wb.createFont();
        font11Bold.setFontHeightInPoints((short)11);
        font11Bold.setFontName("Times New Roman");
        font11Bold.setBold(true);

        Font font11 = wb.createFont();
        font11.setFontHeightInPoints((short)11);
        font11.setFontName("Times New Roman");

        CellStyle style4;
        style4 = wb.createCellStyle();
        style4.setAlignment(HorizontalAlignment.LEFT);
        style4.setFont(font11Bold);

        CellStyle style5;
        style5 = wb.createCellStyle();
        style5.setAlignment(HorizontalAlignment.LEFT);
        style5.setFont(font11);


        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("2. Проверки проведены приборами:");
        cell.setCellStyle(style4);
        countRow++;

        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("1 :    Тип -  " + param.get("type") +"; ");
        cell.setCellStyle(style5);

        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Заводской номер - " + param.get("numberZav") +";");
        cell.setCellStyle(style5);

        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Диапазон измерения - " + param.get("range") +";");
        cell.setCellStyle(style5);

        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Класс точности - " + param.get("class_toch") +";");
        cell.setCellStyle(style5);

        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Дата поверки : последняя - " + param.get("lastDate") + ", очередная - "+ param.get("nextDate") +";");
        cell.setCellStyle(style5);

        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        № аттестата (св-ва) - " + param.get("numberSvid") +";");
        cell.setCellStyle(style5);

        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Орган гос. метрологической службы, проводивший поверку - " + param.get("organ") +".");
        cell.setCellStyle(style5);


        countRow += 2;
        row = sheetUzo.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Заключение:");
        cell.setCellStyle(style4);

        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(2);
        cell.setCellValue("Измеренные значения соответствуют требованиям ГОСТ Р 51327.1-2010,");
        cell.setCellStyle(style5);

        row = sheetUzo.createRow(++countRow);
        cell = row.createCell(2);
        cell.setCellValue("за исключением пунктов указанных в п/п ______");
        cell.setCellStyle(style5);

        countRow += 2;
        row = sheetUzo.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Испытания провели:");
        cell.setCellStyle(style5);
        cell = row.createCell(2);
        cell.setCellValue("Инженер");
        cell.setCellStyle(style5);
        cell = row.createCell(5);
        cell.setCellValue("______");
        cell.setCellStyle(style5);
        cell = row.createCell(8);
        cell.setCellValue(param.get("ingener"));
        cell.setCellStyle(style5);

        if (!param.get("ingener2").isEmpty()){
            countRow += 2;
            row = sheetUzo.createRow(countRow);
            cell = row.createCell(2);
            cell.setCellValue("Инженер");
            cell.setCellStyle(style5);
            cell = row.createCell(5);
            cell.setCellValue("______");
            cell.setCellStyle(style5);
            cell = row.createCell(8);
            cell.setCellValue(param.get("ingener"));
            cell.setCellStyle(style5);
        }

        countRow += 2;
        row = sheetUzo.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Протокол проверил:   Руководитель  лаборатории");
        cell.setCellStyle(style5);
        cell = row.createCell(5);
        cell.setCellValue("______");
        cell.setCellStyle(style5);
        cell = row.createCell(8);
        cell.setCellValue(param.get("rukovoditel"));
        cell.setCellStyle(style5);

        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetUzo), // индекс листа
                0, // начало столбца
                11, // конец столбца
                0, //начало строки
                countRow  // конец строки
        );

        return wb;
    }
}
