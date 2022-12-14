package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.ExcelStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class TitulReport {
    public static Workbook generateVO (Workbook wb, ReportEntity report) {
        Sheet sheetTitul = wb.getSheet("Titul");

        ExcelStyle excelStyle = new ExcelStyle(wb);

        Font fontBig;
        fontBig = wb.createFont();
        fontBig.setFontHeightInPoints((short)24);
        fontBig.setFontName("Times New Roman");
        fontBig.setBold(true);

        CellStyle styleNumberReport= wb.getCellStyleAt(4);
        styleNumberReport.setWrapText(true);
        styleNumberReport.setFont(fontBig);
        styleNumberReport.setAlignment(HorizontalAlignment.CENTER);
        styleNumberReport.setVerticalAlignment(VerticalAlignment.CENTER);

        // ТЕХНИЧЕСКИЙ ОТЧЕТ №
        Row row = sheetTitul.createRow(12);
        Cell cell = row.createCell(0);
        cell.setCellValue("ТЕХНИЧЕСКИЙ ОТЧЕТ № "+report.getNumberReport());
        cell.setCellStyle(styleNumberReport);

        // Адрес объекта
        row = sheetTitul.createRow(19);
        cell = row.createCell(0);
        cell.setCellValue(report.getAddress());
        cell.setCellStyle(excelStyle.styleBoldLined);

        // Наименование объекта
        row = sheetTitul.createRow(23);
        cell = row.createCell(0);
        cell.setCellValue(report.getObject());
        cell.setCellStyle(excelStyle.styleBoldLined);

        // Заказчик
        row = sheetTitul.createRow(26);
        cell = row.createCell(0);
        cell.setCellValue(report.getCustomer());
        cell.setCellStyle(excelStyle.styleBoldLined);




        return wb;

    }
}

