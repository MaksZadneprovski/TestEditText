package com.example.testedittext.report_creator;

import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;

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

public class TitulReport {
    static Sheet sheetTitul;
    static final float  constHeight = 25.0F;
    public static Workbook generateTitul(Workbook wb, ReportEntity report, Map<String, String> param, String date) {
        sheetTitul = wb.getSheet("Titul");

        Font fontBig;
        fontBig = wb.createFont();
        fontBig.setFontHeightInPoints((short)24);
        fontBig.setFontName("Times New Roman");
        fontBig.setBold(true);

        Font font14;
        font14 = wb.createFont();
        font14.setBold(true);
        font14.setFontHeightInPoints((short)14);
        font14.setFontName("Times New Roman");


        Font font = wb.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("Times New Roman");


        CellStyle style;
        style = wb.createCellStyle();
        style.setWrapText(true);
        style.setFont(font);
        style.setBorderRight(BorderStyle.NONE);
        style.setBorderLeft(BorderStyle.NONE);
        style.setBorderBottom(BorderStyle.NONE);
        style.setBorderTop(BorderStyle.NONE);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle styleNumberReport= wb.createCellStyle();
        styleNumberReport.setWrapText(true);
        styleNumberReport.setFont(fontBig);
        styleNumberReport.setAlignment(HorizontalAlignment.CENTER);
        styleNumberReport.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle style2= wb.createCellStyle();
        style2.setWrapText(true);
        style2.setFont(font14);
        style2.setAlignment(HorizontalAlignment.RIGHT);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        Row row;
        Cell cell;

        // Руков. лаб.
        row = sheetTitul.createRow(14);
        cell = row.createCell(0);
        cell.setCellValue("Начальник электролаборатории:  __________ " + param.get("rukovoditel"));
        cell.setCellStyle(style2);

        // ТЕХНИЧЕСКИЙ ОТЧЕТ №
        row = sheetTitul.createRow(20);
        cell = row.createCell(0);
        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((3 * sheetTitul.getDefaultRowHeightInPoints()));
        //cell.setCellValue("ТЕХНИЧЕСКИЙ ОТЧЕТ № "+report.getNumberReport());

        cell.setCellValue("ТЕХНИЧЕСКИЙ ОТЧЕТ № "+ date);
        cell.setCellStyle(styleNumberReport);

        // Дата испытаний
        row = sheetTitul.getRow(27);
        cell = row.getCell(6);
        cell.setCellValue(report.getDate());
        cell.setCellStyle(style);

        // Заказчик
        row = sheetTitul.getRow(28);
        row.setHeightInPoints(getStrokeHeight(report.getCustomer()));
        cell = row.getCell(6);
        row.setHeightInPoints(getStrokeHeight(report.getCustomer()));
        cell.setCellValue(report.getCustomer());
        cell.setCellStyle(style);

        // Наименование объекта
        row = sheetTitul.getRow(29);
        row.setHeightInPoints( getStrokeHeight(report.getObject()));
        cell = row.getCell(6);
        cell.setCellValue(report.getObject());
        cell.setCellStyle(style);

        // Адрес объекта
        row = sheetTitul.getRow(30);
        row.setHeightInPoints(getStrokeHeight(report.getAddress()));
        cell = row.getCell(6);
        cell.setCellValue(report.getAddress());
        cell.setCellStyle(style);


        return wb;

    }

    static float getStrokeHeight(String s){
        return (float) ((s.length() / constHeight + 1.0) * sheetTitul.getDefaultRowHeightInPoints());
    }
}

