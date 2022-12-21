package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.ExcelData;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class VOReport {
    public static Workbook generateVO (Workbook wb, ReportEntity report) {
        Sheet sheetVO = wb.getSheet("VO");

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetVO, 5, report, wb);

        Font fontBig;
        fontBig = wb.createFont();
        fontBig.setFontHeightInPoints((short)18);
        fontBig.setFontName("Times New Roman");
        fontBig.setBold(true);

        CellStyle styleTitulReport= wb.createCellStyle();
        styleTitulReport.setWrapText(true);
        styleTitulReport.setFont(fontBig);
        styleTitulReport.setAlignment(HorizontalAlignment.CENTER);
        styleTitulReport.setVerticalAlignment(VerticalAlignment.CENTER);

        // Заголовок отчета
        Row row = sheetVO.createRow(6);
        Cell cell = row.createCell(0);
        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((2 * sheetVO.getDefaultRowHeightInPoints()));
        cell.setCellValue("ПРОТОКОЛ № "+ ExcelData.numberVOProtocol + " Визуального осмотра");
        cell.setCellStyle(styleTitulReport);




        return wb;

        }
    }
