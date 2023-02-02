package com.example.testedittext.report_creator;

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

public class MSReport {

    public static Workbook generateMS(Workbook wb, ReportEntity report, Map<String, String> param){
        Sheet sheetMS = wb.getSheet("MS");

        // Create a new font and alter it.
        Font font8 = wb.createFont();
        font8.setFontHeightInPoints((short)8);
        font8.setFontName("Times New Roman");
        font8.setBold(false);

        Font font14 = wb.createFont();
        font14.setFontHeightInPoints((short)16);
        font14.setFontName("Times New Roman");
        font14.setBold(true);

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
        styleTitle.setFont(font14);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Стиль для рамки в конце таблицы
        CellStyle styleEndTable;
        styleEndTable = wb.createCellStyle();
        styleEndTable.setBorderLeft(BorderStyle.THIN);
        styleEndTable.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        Row row = sheetMS.createRow(10);
        Cell cell = row.createCell(0);
        cell.setCellValue("ПРОТОКОЛ № " + ExcelData.numberMSProtocol + " Проверки наличия цепи между заземленными ");
        cell.setCellStyle(styleTitle);

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetMS, 5, report, wb );

        // Заполняем строку погоды
        Report.fillWeather(sheetMS, 14,  report, wb);

        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
        int countRow = 26;
        int paragraph = 1;

        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);
                row = sheetMS.createRow(countRow);

                // Для удаления строки с названием щита, если там не оказалось метсвязей
                boolean presenceOfMS = false;

                // Объединяем столбцы для вставки названия щита
                sheetMS.addMergedRegion(new CellRangeAddress(
                        countRow, //first row (0-based)
                        countRow, //last row  (0-based)
                        1, //first column (0-based)
                        5  //last column  (0-based)
                ));

                // Вставляем название щита
                cell = row.createCell(1);
                ////////////////////////////////////////////////////////////////////////
                cell.setCellValue(shield.getName());
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellStyle(styleEndTable);

                countRow++;

                ArrayList<MetallicBond> metallicBonds = shield.getMetallicBonds();

                if (metallicBonds != null) {
                    // Проход по группам
                    for (int j = 0; j < metallicBonds.size(); j++) {
                        MetallicBond metallicBond = metallicBonds.get(j);
                        if (!metallicBond.getPeContact().isEmpty()){
                            row = sheetMS.createRow(countRow);

                            // Строки с метсвязью есть, значит название щита тоже есть в отчете
                            presenceOfMS = true;

                            // Столбец пункт
                            cell = row.createCell(1);
                            cell.setCellValue(paragraph++);
                            cell.setCellStyle(style);

                            // Столбец местоположение и наименование эл.оборудования
                            cell = row.createCell(2);
                            cell.setCellValue(metallicBond.getPeContact());
                            cell.setCellStyle(style);

                            // Столбец количество
                            cell = row.createCell(3);
                            cell.setCellValue(metallicBond.getCountElements());
                            cell.setCellStyle(style);

                            // Столбец R
                            cell = row.createCell(4);
                            if (metallicBond.isNoPe()) cell.setCellValue(">0,05");
                            else cell.setCellFormula(ExcelFormula.randomMS);
                            cell.setCellStyle(style);

                            // Столбец соотв
                            cell = row.createCell(5);
                            if (metallicBond.isNoPe()) cell.setCellValue("не соотв.");
                            else cell.setCellValue("соотв.");
                            cell.setCellStyle(style);

                            countRow++;

                            // Столбец после таблицы
                            cell = row.createCell(6);
                            cell.setCellStyle(styleEndTable);
                        }
                    }
                }

                if (!presenceOfMS) {
                    sheetMS.removeMergedRegion(sheetMS.getNumMergedRegions() - 1);
                    sheetMS.createRow(countRow);
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


        row = sheetMS.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("2. Проверки проведены приборами:");
        cell.setCellStyle(style4);
        countRow++;

        row = sheetMS.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("1 :    Тип -  " + param.get("type") +"; ");
        cell.setCellStyle(style5);

        row = sheetMS.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Заводской номер - " + param.get("numberZav") +";");
        cell.setCellStyle(style5);

        row = sheetMS.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Диапазон измерения - " + param.get("range") +";");
        cell.setCellStyle(style5);

        row = sheetMS.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Класс точности - " + param.get("range") +";");
        cell.setCellStyle(style5);

        row = sheetMS.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Дата поверки : последняя - " + param.get("lastDate") + "очередная - "+ param.get("nextDate") +";");
        cell.setCellStyle(style5);

        row = sheetMS.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        № аттестата (св-ва) - " + param.get("numberSvid") +";");
        cell.setCellStyle(style5);

        row = sheetMS.createRow(++countRow);
        cell = row.createCell(1);
        cell.setCellValue("        Орган гос. метрологической службы, проводивший поверку - " + param.get("organ") +".");
        cell.setCellStyle(style5);



        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetMS), // индекс листа
                0, // начало столбца
                6, // конец столбца
                0, //начало строки
                countRow - 1 // конец строки
        );

        return wb;
    }
}
