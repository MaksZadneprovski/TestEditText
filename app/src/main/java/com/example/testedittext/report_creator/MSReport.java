package com.example.testedittext.report_creator;

import com.example.testedittext.entities.MetallicBond;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.ExcelData;
import com.example.testedittext.utils.ExcelFormula;
import com.example.testedittext.utils.ExcelStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;

public class MSReport {

    public static Workbook generateMS(Workbook wb, ReportEntity report){
        Sheet sheetMS = wb.getSheet("MS");
        ExcelStyle excelStyle = new ExcelStyle(wb);

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetMS, 5, report, excelStyle );

        // Заполняем строку погоды
        Report.fillWeather(sheetMS, 14,  report, excelStyle);

        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
        int countRow = 26;
        int paragraph = 1;
        Row row;


        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);
                row = sheetMS.createRow(countRow);

                Cell cell;

                // Объединяем столбцы для вставки названия щита
                sheetMS.addMergedRegion(new CellRangeAddress(
                        countRow, //first row (0-based)
                        countRow, //last row  (0-based)
                        1, //first column (0-based)
                        5  //last column  (0-based)
                ));

                // Вставляем название щита
                cell = row.createCell(1);
                ////////////////////////////////////////////////////////////////////////
                cell.setCellValue(shield.getName());
                cell.setCellStyle(excelStyle.style);

                countRow++;

                ArrayList<MetallicBond> metallicBonds = shield.getMetallicBonds();

                if (metallicBonds != null) {
                    // Проход по группам
                    for (int j = 0; j < metallicBonds.size(); j++) {
                        MetallicBond metallicBond = metallicBonds.get(j);
                        if (!metallicBond.getPeContact().isEmpty()){
                            row = sheetMS.createRow(countRow);

                            // Столбец пункт
                            cell = row.createCell(1);
                            cell.setCellValue(paragraph++);
                            cell.setCellStyle(excelStyle.style);

                            // Столбец местоположение и наименование эл.оборудования
                            cell = row.createCell(2);
                            cell.setCellValue(metallicBond.getPeContact());
                            cell.setCellStyle(excelStyle.style);

                            // Столбец количество
                            cell = row.createCell(3);
                            cell.setCellValue(metallicBond.getCountElements());
                            cell.setCellStyle(excelStyle.style);

                            // Столбец R
                            cell = row.createCell(4);
                            if (metallicBond.isNoPe()) cell.setCellValue(">0,05");
                            else cell.setCellFormula(ExcelFormula.randomMS);
                            cell.setCellStyle(excelStyle.style);

                            // Столбец соотв
                            cell = row.createCell(5);
                            if (metallicBond.isNoPe()) cell.setCellValue("не соотв.");
                            else cell.setCellValue("соотв.");
                            cell.setCellStyle(excelStyle.style);

                            countRow++;
                        }
                    }
                }
            }
        }

        //устанавливаем область печати
        wb.setPrintArea(
                ExcelData.indexInsulationSheet, // индекс листа
                0, // начало столбца
                5, // конец столбца
                0, //начало строки
                countRow - 1 // конец строки
        );

        return wb;
    }
}
