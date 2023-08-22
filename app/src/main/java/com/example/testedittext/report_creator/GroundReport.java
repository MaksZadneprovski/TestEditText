package com.example.testedittext.report_creator;

import static com.example.testedittext.report_creator.Report.fillRekvizity;

import android.content.Context;

import com.example.testedittext.entities.Ground;
import com.example.testedittext.entities.GroundingDevice;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.enums.TypeOfWork;
import com.example.testedittext.utils.Excel;
import com.example.testedittext.utils.ExcelFormula;
import com.example.testedittext.utils.Storage;

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
import java.util.Map;

public class GroundReport {
    static final float number_of_characters_per_line = 25.0F;
    public static Workbook generateGround(Workbook wb, ReportEntity report, Map<String, String> param, Context context){

        Sheet sheetGround = wb.getSheet("Ground");

        // Create a new font and alter it.
        Font font14 = wb.createFont();
        font14.setFontHeightInPoints((short)14);
        font14.setFontName("Times New Roman");
        font14.setBold(false);

        Font font14B = wb.createFont();
        font14B.setFontHeightInPoints((short)14);
        font14B.setFontName("Times New Roman");
        font14B.setBold(true);

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
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font14B);
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
        styleInf.setFont(font14B);
        styleInf.setAlignment(HorizontalAlignment.LEFT);
        styleInf.setVerticalAlignment(VerticalAlignment.CENTER);

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetGround, 6,number_of_characters_per_line, report, wb);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Заполняем строку погоды
        Report.fillWeather(sheetGround, 10,  report, wb);

        Row row = sheetGround.createRow(6);
        Cell cell = row.createCell(0);
        cell.setCellValue("ПРОТОКОЛ № " + Excel.numberGroundingProtocol );
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

                        if (!groundingDevice.getDestination().isEmpty() && !groundingDevice.getPlace().isEmpty()){

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
                            String izmerR = groundingDevice.getIzmerR();
                            izmerR = izmerR.replace(".",",");
                            cell.setCellValue(izmerR);
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
                }

            // Приборы и закалючение
            Font font11Bold = wb.createFont();
            font11Bold.setFontHeightInPoints((short)11);
            font11Bold.setFontName("Times New Roman");
            font11Bold.setBold(true);

            Font font12N = wb.createFont();
            font12N.setFontHeightInPoints((short)12);
            font12N.setFontName("Times New Roman");

            CellStyle style4;
            style4 = wb.createCellStyle();
            style4.setAlignment(HorizontalAlignment.LEFT);
            style4.setFont(font11Bold);

            CellStyle style5;
            style5 = wb.createCellStyle();
            style5.setAlignment(HorizontalAlignment.LEFT);
            style5.setFont(font12N);


            countRow = Excel.printInstruments(context, sheetGround, countRow, style5, TypeOfWork.Grounding.toString());


            countRow += 2;
            row = sheetGround.createRow(countRow);
            cell = row.createCell(1);
            cell.setCellValue("Заключение:");
            cell.setCellStyle(style4);

            row = sheetGround.createRow(++countRow);
            cell = row.createCell(1);
            cell.setCellValue("        Сопротивление заземляющих устройств соответствуют  требованиям п. 6.4.3.7.2 ГОСТ Р 50571.16—2019; ГОСТ Р 58882—2020 табл. В.1; СТО 34.01-23.1-001-2017,");
            cell.setCellStyle(style5);

            row = sheetGround.createRow(++countRow);
            cell = row.createCell(1);
            cell.setCellValue("         за исключением пунктов указанных в п/п ______");
            cell.setCellStyle(style5);


            countRow += 2;

            // Заполняем Фамилии, Должности и т.д.
            countRow = fillRekvizity(countRow, sheetGround, wb, param, 2,6,8);


            // Получаем количество страниц (Значение неточное, может быть посчитано неточно)
            int countRowInList = 62;
            Storage.pagesCountGround = (int) Math.ceil((double) countRow / countRowInList);


            //устанавливаем область печати
            wb.setPrintArea(
                    wb.getSheetIndex(sheetGround), // индекс листа
                    0, // начало столбца
                    10, // конец столбца
                    0, //начало строки
                    countRow  // конец строки
            );
        }

        return wb;
    }
}
