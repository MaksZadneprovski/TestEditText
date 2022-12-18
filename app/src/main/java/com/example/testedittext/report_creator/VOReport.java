package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class VOReport {
    public static Workbook generateVO (Workbook wb, ReportEntity report) {
        Sheet sheetVO = wb.getSheet("VO");

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetVO, 23, report, wb);


        return wb;

        }
    }
