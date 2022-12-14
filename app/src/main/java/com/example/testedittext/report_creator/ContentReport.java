package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.enums.TypeOfWork;
import com.example.testedittext.utils.ExcelStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Set;

public class ContentReport {

    public static Workbook generateVO (Workbook wb, ReportEntity report) {
        Sheet sheetTitul = wb.getSheet("Titul");

        ExcelStyle excelStyle = new ExcelStyle(wb);

        Row row;
        Cell cell;

        int protocolNumber = 1;
        int countRow = 16;
        String text = "ПРОТОКОЛ № ";

        excelStyle.styleBoldLined.setFont(excelStyle.font14UnLined);

        Set<TypeOfWork> type_of_work = report.getType_of_work();

        if (type_of_work.contains(TypeOfWork.Visual)){
            // Визуального осмотра
            row = sheetTitul.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(text + protocolNumber + " Визуального осмотра");
            cell.setCellStyle(excelStyle.styleBoldLined);
            countRow++;
            protocolNumber++;
        }

        if (type_of_work.contains(TypeOfWork.Visual)){
            // Проверки наличия цепи между заземленными установками и элементами заземленной установки
            row = sheetTitul.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(text + protocolNumber + " Проверки наличия цепи между заземленными установками и элементами заземленной установки");
            cell.setCellStyle(excelStyle.styleBoldLined);
            countRow++;
            protocolNumber++;
        }



        excelStyle.styleBoldLined.setFont(excelStyle.font14Lined);

        return wb;

    }
}
