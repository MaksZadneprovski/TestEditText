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

public class MSReport {

    public static Workbook generateMS(Workbook wb, ReportEntity report){
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
            }
        }

        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetMS), // индекс листа
                0, // начало столбца
                5, // конец столбца
                0, //начало строки
                countRow - 1 // конец строки
        );

        return wb;
    }
}
