package com.example.testedittext.report_creator;


import static com.example.testedittext.report_creator.Report.fillRekvizity;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.Excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;


public class VOReport {

    private static  Sheet sheetVO;
    private static  Workbook wBook;
    private static  int countRow = 13;
    static final float number_of_characters_per_line = 40.0F;

    public static Workbook generateVO (Workbook wb, ReportEntity report, Map<String, String> param) {
        sheetVO = wb.getSheet("VO");


        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetVO, 4,number_of_characters_per_line, report, wb);

        Font fontBig;
        fontBig = wb.createFont();
        fontBig.setFontHeightInPoints((short)18);
        fontBig.setFontName("Times New Roman");
        fontBig.setBold(true);

        Font font11 = wb.createFont();
        font11.setFontHeightInPoints((short)11);
        font11.setFontName("Times New Roman");

        CellStyle styleTitulReport= wb.createCellStyle();
        styleTitulReport.setWrapText(true);
        styleTitulReport.setFont(fontBig);
        styleTitulReport.setAlignment(HorizontalAlignment.CENTER);
        styleTitulReport.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle style5;
        style5 = wb.createCellStyle();
        style5.setAlignment(HorizontalAlignment.LEFT);
        style5.setFont(font11);




        // Заголовок отчета
        Row row = sheetVO.createRow(6);
        Cell cell = row.createCell(0);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((2 * sheetVO.getDefaultRowHeightInPoints()));
        cell.setCellValue("ПРОТОКОЛ № "+ Excel.numberVOProtocol + " Визуального осмотра");
        cell.setCellStyle(styleTitulReport);

        Font font12 = wb.createFont();
        font12.setFontHeightInPoints((short)12);
        font12.setFontName("Times New Roman");
        font12.setUnderline(Font.U_SINGLE);

        CellStyle style3;
        style3 = wb.createCellStyle();
        style3.setFont(font12);
        style3.setAlignment(HorizontalAlignment.RIGHT);
        style3.setWrapText(false);

        countRow = 18;

        // Заполняем Фамилии, Должности и т.д.
        fillRekvizity(countRow, sheetVO, wb, param, 2,3,4);

        return wb;

        }


//    private static void fillTableVO(ArrayList<String> list){
//
//        Row row;
//        Cell cell;
//
//        // Высота строки
//        int strokeHeigth = 4;
//
//        Font font = wBook.createFont();
//        font.setFontHeightInPoints((short)12);
//        font.setFontName("Times New Roman");
//
//        CellStyle style;
//        style = wBook.createCellStyle();
//        style.setWrapText(true);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//        style.setBorderTop(BorderStyle.THIN);
//        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
//        style.setFont(font);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        CellStyle style2;
//        style2 = wBook.createCellStyle();
//        style2.setBorderBottom(BorderStyle.THIN);
//        style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//        style2.setBorderRight(BorderStyle.THIN);
//        style2.setBorderLeft(BorderStyle.THIN);
//        style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
//        style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//        style2.setBorderTop(BorderStyle.THIN);
//        style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
//        style2.setAlignment(HorizontalAlignment.CENTER);
//        style2.setVerticalAlignment(VerticalAlignment.CENTER);
//
//

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
//    }

    }
