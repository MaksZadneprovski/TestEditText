package com.example.testedittext.report_creator;

import static com.example.testedittext.report_creator.Report.fillDescription;

import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.DefectsParser;
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

public class DefectsReport {
    static final float number_of_characters_per_line = 25.0F;
    public static Workbook generateDefects(Workbook wb, ReportEntity report, Map<String, String> param){
        Sheet sheetDefects = wb.getSheet("Vedomost");

        // Create a new font and alter it.
        Font font14 = wb.createFont();
        font14.setFontHeightInPoints((short)14);
        font14.setFontName("Times New Roman");
        font14.setBold(false);


        Font fontForSurname = wb.createFont();
        fontForSurname.setFontHeightInPoints((short)11);
        fontForSurname.setFontName("Times New Roman");
        fontForSurname.setUnderline((byte) 1);

        CellStyle styleForSurname;
        styleForSurname = wb.createCellStyle();
        styleForSurname.setAlignment(HorizontalAlignment.LEFT);
        styleForSurname.setFont(fontForSurname);

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
        style.setFont(font14);
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


        Font font11Bold = wb.createFont();
        font11Bold.setFontHeightInPoints((short)11);
        font11Bold.setFontName("Times New Roman");
        font11Bold.setBold(true);

        Font font11 = wb.createFont();
        font11.setFontHeightInPoints((short)11);
        font11.setFontName("Times New Roman");

        CellStyle style4;
        style4 = wb.createCellStyle();
        style4.setAlignment(HorizontalAlignment.CENTER);
        style4.setFont(font11);

        CellStyle style5;
        style5 = wb.createCellStyle();
        style5.setAlignment(HorizontalAlignment.LEFT);
        style5.setFont(font11);

        CellStyle style5Center;
        style5Center = wb.createCellStyle();
        style5Center.setAlignment(HorizontalAlignment.CENTER);
        style5Center.setFont(font11);

        Row row ;
        Cell cell;

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetDefects, 4,number_of_characters_per_line, report, wb );

        // Начинаем с 9 строки
        int countRow = 8;
        int paragraph = 1;

        // Если дефектов нет, то таблицу нужно удалить
        boolean presenceOfDefectsTable = false;

        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);
                row = sheetDefects.createRow(countRow);

                // Для удаления строки с названием щита, если там не оказалось дефектов
                Storage.presenceOfDefects = false;

                // Объединяем столбцы для вставки названия щита
                sheetDefects.addMergedRegion(new CellRangeAddress(
                        countRow, //first row (0-based)
                        countRow, //last row  (0-based)
                        1, //first column (0-based)
                        4  //last column  (0-based)
                ));

                // Вставляем название щита
                cell = row.createCell(1);
                ////////////////////////////////////////////////////////////////////////
                cell.setCellValue(shield.getName());
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellStyle(styleEndTable);

                countRow++;

                ArrayList<Defect> defects = shield.getDefects();

                if (defects != null) {
                    // Проход по группам
                    for (int j = 0; j < defects.size(); j++) {
                        Defect defect = defects.get(j);
                        if (!defect.getDefect().isEmpty()){
                            row = sheetDefects.createRow(countRow);

                            // Строка с дефектом есть, значит название щита тоже есть в отчете и таблицу удалять не нужно
                            Storage.presenceOfDefects = true;
                            presenceOfDefectsTable = true;

                            // Столбец пункт
                            cell = row.createCell(1);
                            cell.setCellValue(paragraph++);
                            cell.setCellStyle(style);

                            // Столбец Выявленный дефект, несоответствие
                            cell = row.createCell(2);
                            cell.setCellValue(defect.getDefect());
                            cell.setCellStyle(style);

                            // Столбец Основание
                            cell = row.createCell(3);
                            cell.setCellValue(DefectsParser.getString3FromMap(Storage.defects, defect.getDefect()));
                            cell.setCellStyle(style);

                            // Столбец Примечание
                            cell = row.createCell(4);
                            cell.setCellValue(defect.getNote());
                            cell.setCellStyle(style);

                            countRow++;

                            // Столбец после таблицы
                            cell = row.createCell(5);
                            cell.setCellStyle(styleEndTable);
                        }
                    }
                }

                if (!Storage.presenceOfDefects) {
                    sheetDefects.removeMergedRegion(sheetDefects.getNumMergedRegions() - 1);
                    sheetDefects.createRow(countRow);
                    row.createCell(1);
                    cell.setCellValue("");
                    // Так как в этом стиле нет рамок
                    cell.setCellStyle(styleTitle);
                    countRow--;
                }
            }
        }

        // Удаление ттаблицы
        if (!presenceOfDefectsTable){
            countRow = 7;
            row = sheetDefects.createRow(countRow);

            for (int i = 1; i < 5; i++) {
                cell = row.createCell(1);
                cell.setCellValue(" ");
                cell.setCellStyle(style5);
            }


            row = sheetDefects.createRow(countRow);
            cell = row.createCell(1);
            cell.setCellValue("В ходе проведения проверок и испытаний дефектов не обнаружено");
            cell.setCellStyle(style5);

        }

        // закалючение
        countRow += 2;
        row = sheetDefects.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Проверки и испытания производились по методикам согласно ГОСТ Р 50571.16-2019");
        cell.setCellStyle(style5);

        countRow += 1;
        row = sheetDefects.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Примечание: Осмотр, измерения, испытания, опробования электрооборудования произведены в пределах доступности.");
        cell.setCellStyle(style5);


        countRow += 2;
        row = sheetDefects.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Ведомость составил:");
        cell.setCellStyle(styleForSurname);
        countRow += 1;
        row = sheetDefects.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Руководитель  лаборатории");
        cell.setCellStyle(styleForSurname);
        cell = row.createCell(3);
        cell.setCellValue("____________");
        cell.setCellStyle(style5Center);
        cell = row.createCell(4);
        cell.setCellValue(param.get("rukovoditel"));
        cell.setCellStyle(styleForSurname);

        countRow+=1;
        row = sheetDefects.createRow(countRow);

        // Заполняем долж. подп. ФИО
        fillDescription(row,1,3,4, style5, style4);

        countRow += 2;

        if (presenceOfDefectsTable){

            sheetDefects.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    7  //last column  (0-based)
            ));

            row = sheetDefects.createRow(countRow);
            cell = row.createCell(1);
            cell.setCellValue("Дефекты и недостатки, выявленные электроизмерительной лабораторией, устранены «___» ___________202__г.");
            cell.setCellStyle(style4);

            countRow += 2;
            sheetDefects.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    7  //last column  (0-based)
            ));
            row = sheetDefects.createRow(countRow);
            cell = row.createCell(1);
            cell.setCellValue("Ответственный представитель «Заказчика» ____________________");
            cell.setCellStyle(style4);

            countRow += 3;
            sheetDefects.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    7  //last column  (0-based)
            ));
            row = sheetDefects.createRow(countRow);
            cell = row.createCell(1);
            cell.setCellValue("Устранение дефектов проверено «___» _____________ 202__ г. _________________ м.п.");
            cell.setCellStyle(style4);
        }


        countRow += 2;
        sheetDefects.addMergedRegion(new CellRangeAddress(
                countRow, //first row (0-based)
                countRow, //last row  (0-based)
                0, //first column (0-based)
                7  //last column  (0-based)
        ));
        row = sheetDefects.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Частичная или полная перепечатка и размножение только с разрешения испытательной лаборатории.");
        cell.setCellStyle(style4);

        countRow += 1;
        sheetDefects.addMergedRegion(new CellRangeAddress(
                countRow, //first row (0-based)
                countRow, //last row  (0-based)
                0, //first column (0-based)
                7  //last column  (0-based)
        ));
        row = sheetDefects.createRow(countRow);
        cell = row.createCell(1);
        cell.setCellValue("Исправления не допускаются.");
        cell.setCellStyle(style4);



        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetDefects), // индекс листа
                0, // начало столбца
                7, // конец столбца
                0, //начало строки
                countRow - 1 // конец строки
        );

        return wb;
    }
}
