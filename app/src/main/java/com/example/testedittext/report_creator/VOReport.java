package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.ExcelData;

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

import java.util.ArrayList;


public class VOReport {

    private static  Sheet sheetVO;
    private static  Workbook wBook;
    private static  int countRow = 13;

    public static Workbook generateVO (Workbook wb, ReportEntity report, String ingener, String rukovoditel) {
        sheetVO = wb.getSheet("VO");


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
    private static void fillTableVO(ArrayList<String> list){

        Row row;
        Cell cell;

        // Высота строки
        int strokeHeigth = 4;

        Font font = wBook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("Times New Roman");

        CellStyle style;
        style = wBook.createCellStyle();
        style.setWrapText(true);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle style2;
        style2 = wBook.createCellStyle();
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style2.setBorderTop(BorderStyle.THIN);
        style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);



//        row = sheetVO.createRow(countRow);
//
//        for (int i = ; i < ; i++) {
//            cell = row.createCell(i);
//            cell.setCellValue(list.get(i-1));
//            cell.setCellStyle(style);
//        }
//
//        cell = row.createCell();
//        cell.setCellValue(list.get());
//        cell.setCellStyle(style2);
//
//        //увеличиваем высоту строки, чтобы вместить две строки текста
//        row.setHeightInPoints((strokeHeigth * sheetVO.getDefaultRowHeightInPoints()));
//        countRow++;
    }

    }
