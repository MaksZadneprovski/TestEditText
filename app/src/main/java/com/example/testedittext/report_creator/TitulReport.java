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
    public static Workbook generateTitul(Workbook wb, ReportEntity report, Map<String, String> param, String date) {
        Sheet sheetTitul = wb.getSheet("Titul");

        Font fontBig;
        fontBig = wb.createFont();
        fontBig.setFontHeightInPoints((short)24);
        fontBig.setFontName("Times New Roman");
        fontBig.setBold(true);

        Font font11;
        font11 = wb.createFont();
        font11.setBold(true);
        font11.setFontHeightInPoints((short)11);
        font11.setFontName("Times New Roman");


        Font font = wb.createFont();
        font.setUnderline(Font.U_SINGLE);
        font.setFontHeightInPoints((short)14);
        font.setFontName("Times New Roman");
        font.setBold(true);

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

        CellStyle styleNumberReport= wb.createCellStyle();
        styleNumberReport.setWrapText(true);
        styleNumberReport.setFont(fontBig);
        styleNumberReport.setAlignment(HorizontalAlignment.CENTER);
        styleNumberReport.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle style2= wb.createCellStyle();
        style2.setWrapText(true);
        style2.setFont(font11);
        style2.setAlignment(HorizontalAlignment.RIGHT);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);

        // ТЕХНИЧЕСКИЙ ОТЧЕТ №
        Row row = sheetTitul.createRow(12);
        Cell cell = row.createCell(0);
        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((3 * sheetTitul.getDefaultRowHeightInPoints()));
        //cell.setCellValue("ТЕХНИЧЕСКИЙ ОТЧЕТ № "+report.getNumberReport());

        cell.setCellValue("ТЕХНИЧЕСКИЙ ОТЧЕТ № "+ date);
        cell.setCellStyle(styleNumberReport);

        // Адрес объекта
        row = sheetTitul.createRow(19);
        cell = row.createCell(0);
        cell.setCellValue(report.getAddress());
        cell.setCellStyle(style);

        // Наименование объекта
        row = sheetTitul.createRow(23);
        cell = row.createCell(0);
        cell.setCellValue(report.getObject());
        cell.setCellStyle(style);

        // Заказчик
        row = sheetTitul.createRow(26);
        cell = row.createCell(0);
        cell.setCellValue(report.getCustomer());
        cell.setCellStyle(style);

        // Дата испытаний
        row = sheetTitul.createRow(31);
        cell = row.createCell(0);
        cell.setCellValue("Испытания проведены: " + report.getDate());
        cell.setCellStyle(style2);

        // Руков. лаб.
        row = sheetTitul.createRow(35);
        cell = row.createCell(0);
        cell.setCellValue("МП ________" + param.get("rukovoditel"));
        cell.setCellStyle(style2);

        return wb;

    }
}

