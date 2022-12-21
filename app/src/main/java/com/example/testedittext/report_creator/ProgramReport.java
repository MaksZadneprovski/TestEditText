package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.enums.TypeOfWork;

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
import java.util.Set;

public class ProgramReport {

    private static  Sheet sheetProgram;
    private static  Workbook wBook;
    private static  int countRow = 13;


    public static Workbook generateVO (Workbook wb, ReportEntity report) {
        sheetProgram = wb.getSheet("Program");
        wBook = wb;

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetProgram, 5, report, wb);


        Set<TypeOfWork> type_of_work = report.getType_of_work();

        // Визуальный осмотр
        if (type_of_work.contains(TypeOfWork.Visual)){

            ArrayList<String> list = new ArrayList<>();
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");

            fillProgramm(list);

        }

        // Метсвязь
        if (type_of_work.contains(TypeOfWork.Visual)){


        }

        // Изоляция
        if (type_of_work.contains(TypeOfWork.Insulation)){

        }

        // Петля
        if (type_of_work.contains(TypeOfWork.PhaseZero)){

        }

        // Заземляющее устройство
        if (type_of_work.contains(TypeOfWork.Grounding)){

        }

        // УЗО
        if (type_of_work.contains(TypeOfWork.Uzo)){

        }

        // Прогрузка
        if (type_of_work.contains(TypeOfWork.Avtomat)){

        }

        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetProgram), // индекс листа
                0, // начало столбца
                9, // конец столбца
                0, //начало строки
                50 // конец строки
        );

        return wb;

    }

    private static void fillProgramm(ArrayList<String> list){

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



        row = sheetProgram.createRow(countRow);

        for (int i = 1; i < 4; i++) {
            cell = row.createCell(i);
            cell.setCellValue(list.get(i-1));
            cell.setCellStyle(style);
        }

        cell = row.createCell(4);
        cell.setCellValue(list.get(3));
        cell.setCellStyle(style2);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((strokeHeigth * sheetProgram.getDefaultRowHeightInPoints()));
        countRow++;
    }
}
