package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

public class Zakluchenie {

    private static Sheet sheetZakl;

    public static Workbook generateZakl(Workbook wb, ReportEntity report, Map<String, String> param) {
        sheetZakl = wb.getSheet("Zakluchenie");


        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetZakl, 9, report, wb);

        return wb;
    }
}
