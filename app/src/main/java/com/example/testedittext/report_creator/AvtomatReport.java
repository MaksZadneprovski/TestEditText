package com.example.testedittext.report_creator;

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

public class AvtomatReport {

    public static Workbook generateAvtomat(Workbook wb, ReportEntity report, Map<String, String> param){

        Sheet sheetAvtomat = wb.getSheet("Avtomat");

        // Create a new font and alter it.
        Font font8 = wb.createFont();
        font8.setFontHeightInPoints((short)8);
        font8.setFontName("Times New Roman");
        font8.setBold(false);

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
        styleTitle.setWrapText(true);
        styleTitle.setFont(font16);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Стиль для рамки в конце таблицы
        CellStyle styleEndTable;
        styleEndTable = wb.createCellStyle();
        styleEndTable.setBorderLeft(BorderStyle.THIN);
        styleEndTable.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetAvtomat, 15, report, wb);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Заполняем строку погоды
        Report.fillWeather(sheetAvtomat, 11,  report, wb);

        Row row = sheetAvtomat.createRow(8);
        Cell cell = row.createCell(0);
        cell.setCellValue("ПРОТОКОЛ № " + ExcelData.numberAvtomatProtocol + " Проверки действия расцепителей автоматических выключателей до 1000 В");
        cell.setCellStyle(styleTitle);

        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
        int countRow = 28;
        int paragraph = 1;
        String avtomat = "QF";


        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);

                // Для удаления строки с названием щита, если там не оказалось УЗО
                boolean presenceOfAvtomat = false;

                // Получаем группы щита
                ArrayList<Group> shieldGroups = shield.getShieldGroups();

                row = sheetAvtomat.createRow(countRow);

                // Объединяем столбцы для вставки названия щита
                sheetAvtomat.addMergedRegion(new CellRangeAddress(
                        countRow, //first row (0-based)
                        countRow, //last row  (0-based)
                        0, //first column (0-based)
                        15  //last column  (0-based)
                ));

                // Вставляем название щита
                cell = row.createCell(1);
                ////////////////////////////////////////////////////////////////////////
                cell.setCellValue(shield.getName());
                cell.setCellStyle(style);

                cell = row.createCell(16);
                cell.setCellStyle(styleEndTable);

                countRow++;



                // Переменная для автоматической генерации номера автомата (QF1, QF2...)
                int avtomatCount = 1;

                if (shieldGroups != null) {
                    // Проход по группам
                    for (int j = 0; j < shieldGroups.size(); j++) {
                        Group group = shieldGroups.get(j);
                        String defenseApparatus = group.getDefenseApparatus();
                        if (!group.getAddress().isEmpty() && !defenseApparatus.equals("УЗО") && !defenseApparatus.equals("Предохранитель") && !defenseApparatus.isEmpty()) {

                            // Строки с измерениями есть, значит название щита тоже есть в отчете
                            presenceOfAvtomat = true;

                            row = sheetAvtomat.createRow(countRow);

                            // Столбец пункт
                            cell = row.createCell(0);
                            cell.setCellValue(paragraph++);
                            cell.setCellStyle(style);

                            // Столбец местоположение и наименование эл.оборудования
                            cell = row.createCell(1);
                            String lineName = "";
                            if (group.getDesignation().isEmpty()) {
                                lineName = avtomat + avtomatCount + " - " + group.getAddress();
                                avtomatCount++;
                            } else
                                lineName = group.getDesignation() + " - " + group.getAddress();

                            cell.setCellValue(lineName);
                            cell.setCellStyle(style);

                            // Столбец Тип Автомата
                            cell = row.createCell(2);
                            cell.setCellValue(group.getMachineBrand());
                            cell.setCellStyle(style);

                            // Столбец Тип расцепителя
                            cell = row.createCell(3);
                            cell.setCellValue(group.getReleaseType());
                            cell.setCellStyle(style);

                            // Столбец Номин. ток нагрузки In, A
                            cell = row.createCell(4);
                            cell.setCellValue(group.getRatedCurrent());
                            cell.setCellStyle(style);

                            // Столбец Уставка перегрузки (1,45 номинала)
                            cell = row.createCell(5);
                            cell.setCellFormula(ExcelFormula.getUstavkaPeregruz(countRow));
                            cell.setCellStyle(style);

                            // Столбец Уставка KZ
                            cell = row.createCell(6);
                            cell.setCellFormula(ExcelFormula.getRangeForAvtomat(countRow));
                            cell.setCellStyle(style);

                            // Столбец испытательный ток перегруза
                            cell = row.createCell(7);
                            cell.setCellFormula(ExcelFormula.getIspTokPeregruz(countRow));
                            cell.setCellStyle(style);

                            // Столбец доп. время откл. перегрузки
                            cell = row.createCell(8);
                            String ratedCurrent = group.getRatedCurrent();
                            if (ratedCurrent!=null && !ratedCurrent.isEmpty()){

                                int nominalTok = Integer.parseInt(ratedCurrent);
                                if (nominalTok < 32){
                                    cell.setCellValue("1-60");
                                }else {
                                    cell.setCellValue("1-120");
                                }
                            }

                            cell.setCellStyle(style);

                            // Столбец изм. время откл. перегрузки
                            cell = row.createCell(9);
                            cell.setCellFormula(ExcelFormula.getRandomAvtomatPeregruzTime());
                            cell.setCellStyle(style);

                            // Столбец Длит-ть приложения испытательного тока
                            cell = row.createCell(10);
                            cell.setCellValue("0,1");
                            cell.setCellStyle(style);

                            // Столбец Испытательный ток несрабатывания
                            cell = row.createCell(11);
                            cell.setCellFormula(ExcelFormula.getAvtomatTokNeSrab(countRow));
                            cell.setCellStyle(style);

                            // Столбец Реакция расцепителя
                            cell = row.createCell(12);
                            cell.setCellValue("-");
                            cell.setCellStyle(style);

                            // Столбец Испытательный ток несрабатывания
                            cell = row.createCell(13);
                            cell.setCellFormula(ExcelFormula.getAvtomatTokSrab(countRow));
                            cell.setCellStyle(style);

                            // Столбец Реакция расцепителя
                            cell = row.createCell(14);
                            cell.setCellValue("+");
                            cell.setCellStyle(style);

                            // Столбец соотв
                            cell = row.createCell(15);
                            cell.setCellValue("соотв.");
                            cell.setCellStyle(style);

                            cell = row.createCell(16);
                            cell.setCellStyle(styleEndTable);

                            countRow++;
                        } else avtomatCount++;
                    }
                }

                if (!presenceOfAvtomat) {
                    sheetAvtomat.removeMergedRegion(sheetAvtomat.getNumMergedRegions()-1);
                    sheetAvtomat.createRow(countRow);
                    row.createCell(1);
                    cell.setCellValue("");
                    // Так как в этом стиле нет рамок
                    cell.setCellStyle(styleTitle);
                    countRow--;
                }
            }
        }

        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetAvtomat), // индекс листа
                0, // начало столбца
                16, // конец столбца
                0, //начало строки
                countRow - 1 // конец строки
        );

        return wb;
    }
}
