package com.example.testedittext.utils;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelStyle {
    public CellStyle styleBorderNoneRight;
    public CellStyle styleBorderNoneCenter;
    public CellStyle styleBoldLined;
    public Font font8, font10, font14UnLined,font14Lined;
    public CellStyle style;

    Workbook wb;

    public ExcelStyle(Workbook wb) {
        this.wb = wb;

        // Create a new font and alter it.
        font8 = wb.createFont();
        font8.setFontHeightInPoints((short)8);
        font8.setFontName("Times New Roman");
        font8.setBold(false);

        // Create a new font and alter it.
        font10 = wb.createFont();
        font10.setFontHeightInPoints((short)10);
        font10.setFontName("Times New Roman");
        font10.setBold(false);

        font14UnLined = wb.createFont();
        font14UnLined.setFontHeightInPoints((short)14);
        font14UnLined.setFontName("Times New Roman");
        font14UnLined.setUnderline(Font.U_NONE);
        font14UnLined.setBold(true);

        font14Lined = wb.createFont();
        font14Lined.setFontHeightInPoints((short)14);
        font14Lined.setFontName("Times New Roman");
        font14Lined.setUnderline(Font.U_SINGLE);
        font14Lined.setBold(true);

        // Создаем стиль для создания рамки у ячейки
        style = wb.getCellStyleAt(1);
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

        // Создаем стиль для данных об объекте
        styleBorderNoneRight = wb.getCellStyleAt(2);
        styleBorderNoneRight.setBorderTop(BorderStyle.NONE);
        styleBorderNoneRight.setBorderBottom(BorderStyle.NONE);
        styleBorderNoneRight.setBorderLeft(BorderStyle.NONE);
        styleBorderNoneRight.setBorderRight(BorderStyle.NONE);
        styleBorderNoneRight.setWrapText(false);
        styleBorderNoneRight.setFont(font10);
        styleBorderNoneRight.setAlignment(HorizontalAlignment.RIGHT);
        styleBorderNoneRight.setVerticalAlignment(VerticalAlignment.CENTER);

        // Создаем стиль для данных об объекте
        styleBorderNoneCenter = wb.getCellStyleAt(0);
        styleBorderNoneCenter.setBorderTop(BorderStyle.NONE);
        styleBorderNoneCenter.setBorderBottom(BorderStyle.NONE);
        styleBorderNoneCenter.setBorderLeft(BorderStyle.NONE);
        styleBorderNoneCenter.setBorderRight(BorderStyle.NONE);
        styleBorderNoneCenter.setWrapText(false);
        styleBorderNoneCenter.setFont(font10);
        styleBorderNoneCenter.setAlignment(HorizontalAlignment.CENTER);
        styleBorderNoneCenter.setVerticalAlignment(VerticalAlignment.CENTER);

        styleBoldLined = wb.getCellStyleAt(3);
        styleBoldLined.setWrapText(true);
        styleBoldLined.setFont(font14Lined);
        styleBoldLined.setAlignment(HorizontalAlignment.CENTER);
        styleBoldLined.setVerticalAlignment(VerticalAlignment.CENTER);
    }
}
