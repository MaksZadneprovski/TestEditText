package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.ExcelStyle;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;

public class VOReport {
    public static Workbook generateVO (Workbook wb, ReportEntity report) {
        Sheet sheetVO = wb.getSheet("VO");
        ExcelStyle excelStyle = new ExcelStyle(wb);

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetVO, 23, report, excelStyle);

        // Заполняем строку погоды
        Report.fillWeather(sheetVO, 14, report, excelStyle);

        return wb;

        }
    }
