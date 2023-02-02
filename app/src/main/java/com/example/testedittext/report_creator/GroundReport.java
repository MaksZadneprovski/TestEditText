package com.example.testedittext.report_creator;

import com.example.testedittext.entities.Ground;
import com.example.testedittext.entities.GroundingDevice;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.ExcelData;
import com.example.testedittext.utils.ExcelFormula;

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
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.Map;

public class GroundReport {

    public static Workbook generateGround(Workbook wb, ReportEntity report, Map<String, String> param){

        Sheet sheetGround = wb.getSheet("Ground");

        // Create a new font and alter it.
        Font font8 = wb.createFont();
        font8.setFontHeightInPoints((short)8);
        font8.setFontName("Times New Roman");
        font8.setBold(false);

        Font font11 = wb.createFont();
        font11.setFontHeightInPoints((short)11);
        font11.setFontName("Times New Roman");
        font11.setBold(true);

        Font font16 = wb.createFont();
        font16.setFontHeightInPoints((short)16);
        font16.setFontName("Times New Roman");
        font16.setBold(true);

        CellStyle style;
        // Создаем стиль для создания рамки у ячейки
        style = wb.createCellStyle();
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

        CellStyle styleTitle;
        // Создаем стиль для создания рамки у ячейки
        styleTitle = wb.createCellStyle();
        styleTitle.setWrapText(false);
        styleTitle.setFont(font16);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Стиль для рамки в конце таблицы
        CellStyle styleEndTable;
        styleEndTable = wb.createCellStyle();
        styleEndTable.setBorderLeft(BorderStyle.THIN);
        styleEndTable.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        CellStyle styleInf;
        // Создаем стиль для создания рамки у ячейки
        styleInf = wb.createCellStyle();
        styleInf.setWrapText(false);
        styleInf.setFont(font11);
        styleInf.setAlignment(HorizontalAlignment.LEFT);
        styleInf.setVerticalAlignment(VerticalAlignment.CENTER);

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetGround, 9, report, wb);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Заполняем строку погоды
        Report.fillWeather(sheetGround, 10,  report, wb);

        Row row = sheetGround.createRow(7);
        Cell cell = row.createCell(0);
        cell.setCellValue("ПРОТОКОЛ № " + ExcelData.numberGroundingProtocol + " Проверки сопротивлений заземлителей и заземляющих устройств");
        cell.setCellStyle(styleTitle);

        Ground ground = report.getGround();


        if (ground!=null) {
            row = sheetGround.createRow(20);
            cell = row.createCell(2);
            cell.setCellValue("1. Вид грунта: " + ground.getSoilType());
            cell.setCellStyle(styleInf);

            row = sheetGround.createRow(22);
            cell = row.createCell(2);
            cell.setCellValue("2. Характер грунта: " + ground.getSoilCharacter());
            cell.setCellStyle(styleInf);

            row = sheetGround.createRow(24);
            cell = row.createCell(2);
            cell.setCellValue("3. Заземляющее устройство применяется для электроустановки: " + ground.getVoltage());
            cell.setCellStyle(styleInf);

            row = sheetGround.createRow(26);
            cell = row.createCell(2);
            cell.setCellValue("4. Режим нейтрали: " + ground.getNeutralMode());
            cell.setCellStyle(styleInf);


            // Начинаем с 29 строки, первые 28 занимает шапка таблицы
            int countRow = 32;
            int paragraph = 1;

                ArrayList<GroundingDevice> groundingDevices = ground.getGroundingDevices();

                if (groundingDevices != null) {

                    for (int i = 0; i < groundingDevices.size(); i++) {

                        GroundingDevice groundingDevice = groundingDevices.get(i);

                        row = sheetGround.createRow(countRow);

                        // Столбец пункт
                        cell = row.createCell(1);
                        cell.setCellValue(paragraph++);
                        cell.setCellStyle(style);

                        // Столбец Назначение заземлителя
                        cell = row.createCell(2);
                        cell.setCellValue(groundingDevice.getDestination());
                        cell.setCellStyle(style);

                        // Столбец Место проверки
                        cell = row.createCell(3);
                        cell.setCellValue(groundingDevice.getPlace());
                        cell.setCellStyle(style);

                        // Столбец Расстояние до потенциальных и токовых электродов
                        cell = row.createCell(4);
                        if (groundingDevice.getDistanceToElectrodes().isEmpty()) {
                            cell.setCellValue("20");
                        } else {
                            cell.setCellValue(groundingDevice.getDistanceToElectrodes());
                        }
                        cell.setCellStyle(style);

                        // Столбец Доп.
                        cell = row.createCell(5);

                        if (groundingDevice.getDopustR().isEmpty()) {
                            cell.setCellValue("4");
                        } else {
                            cell.setCellValue(groundingDevice.getDopustR());
                        }
                        cell.setCellStyle(style);

                        // Столбец Изм.
                        cell = row.createCell(6);
                        cell.setCellValue(groundingDevice.getIzmerR());
                        cell.setCellStyle(style);

                        // Столбец Привед.
                        cell = row.createCell(7);
                        cell.setCellFormula(ExcelFormula.getPrivedSoprotivZazemlen(countRow));
                        cell.setCellStyle(style);

                        // Столбец Коэф. сезонности
                        cell = row.createCell(8);
                        cell.setCellValue(ground.getSeazonKoef());
                        cell.setCellStyle(style);

                        // Столбец соотв
                        cell = row.createCell(9);
                        cell.setCellValue("соотв.");
                        cell.setCellStyle(style);

                        cell = row.createCell(10);
                        cell.setCellStyle(styleEndTable);

                        countRow++;
                    }
                }


            //устанавливаем область печати
            wb.setPrintArea(
                    wb.getSheetIndex(sheetGround), // индекс листа
                    0, // начало столбца
                    9, // конец столбца
                    0, //начало строки
                    countRow - 1 // конец строки
            );
        }

        return wb;
    }
}
