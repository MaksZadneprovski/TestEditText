package com.example.testedittext.report_creator;

import static com.example.testedittext.report_creator.Report.fillRekvizity;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.Storage;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

public class Zakluchenie {
    static final float number_of_characters_per_line = 30.0F;

    private static Sheet sheetZakl;

    public static Workbook generateZakl(Workbook wb, ReportEntity report, Map<String, String> param) {
        sheetZakl = wb.getSheet("Zakluchenie");


        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetZakl, 6,number_of_characters_per_line, report, wb);

        Row row;
        Cell cell;
        int countRow = 13;

        Font font = wb.createFont();
        font.setUnderline(Font.U_SINGLE);
        font.setFontHeightInPoints((short)14);
        font.setFontName("Times New Roman");
        font.setBold(true);

        Font font11 = wb.createFont();
        font11.setFontHeightInPoints((short)11);
        font11.setFontName("Times New Roman");

        CellStyle style;
        style = wb.createCellStyle();
        style.setWrapText(true);
        style.setFont(font);
        style.setBorderRight(BorderStyle.NONE);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.NONE);
        style.setBorderTop(BorderStyle.NONE);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);


        // Адрес объекта
        row = sheetZakl.createRow(9);
        cell = row.createCell(0);
        cell.setCellValue(report.getObject());
        cell.setCellStyle(style);

        // Наименование объекта
        row = sheetZakl.createRow(11);
        cell = row.createCell(0);
        cell.setCellValue(report.getAddress());
        cell.setCellStyle(style);


        if (Storage.presenceOfDefects){
            // Не соотв.
            row = sheetZakl.createRow(13);
            cell = row.createCell(0);
            cell.setCellValue("соответствует требованиям нормативной документации,");
            cell.setCellStyle(style);
            row = sheetZakl.createRow(15);
            cell = row.createCell(0);
            cell.setCellValue("за исключением пунктов, указанных в дефектной ведомости");
            cell.setCellStyle(style);

            countRow+=2;
        }

        countRow+=4;

        // Заполняем Фамилии, Должности и т.д.
        fillRekvizity(countRow, sheetZakl, wb, param, 2,5,8);

        return wb;
    }
}
