package com.example.testedittext.report_creator;

import android.content.Context;

import com.example.testedittext.R;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.entities.enums.TypeOfWork;
import com.example.testedittext.utils.ExcelData;
import com.example.testedittext.utils.ExcelFormula;
import com.example.testedittext.utils.ExcelStyle;


import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Report {

    Context context;
    private  String fileName;
    ReportEntity report;

    private boolean isF0, isInsulation, isGround, isUzo, isMetallicBond, isVizual;

    ExcelStyle excelStyle;

    public Report(Context context, String fileName, ReportEntity report) {
        this.context = context;
        this.fileName = fileName;
        this.report = report;
    }

    public void generate() throws IOException {

        Workbook wb;
        wb = WorkbookFactory.create(context.getResources().openRawResource(R.raw.report3));

        // Получаем щиты для составления отчета
        ArrayList<Shield> shields = report.getShields();

        // Определяем необходимость тех или иных протоколов
        setNecessaryProtocols();

        // Создаем стили
        excelStyle = new ExcelStyle(wb);

        Sheet sheetInsulation = wb.getSheet("Insulation");
        Sheet sheetF0 = wb.getSheet("F0");

// Созданиие протокола изоляции
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////
        // Заполняем строки заказчик, объект, адрес, дата
        fillMainData(sheetInsulation, 15);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Заполняем строку погоды
        fillWeather(sheetInsulation, 13, 4);

        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
        int countRow = 27;
        int paragraph = 1;

        Row row;
        String avtomat = "QF";
        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);
                row = sheetInsulation.createRow(countRow);

                Cell cell;

                // Объединяем столбцы для вставки названия щита
                sheetInsulation.addMergedRegion(new CellRangeAddress(
                        countRow, //first row (0-based)
                        countRow, //last row  (0-based)
                        0, //first column (0-based)
                        15  //last column  (0-based)
                ));

                // Вставляем название щита
                cell = row.createCell(0);
                ////////////////////////////////////////////////////////////////////////
                cell.setCellValue(shield.getName());
                cell.setCellStyle(excelStyle.style);

                countRow++;
                // Получаем группы щита
                ArrayList<Group> shieldGroups = shield.getShieldGroups();
                // Переменная для автоматической генерации номера автомата (QF1, QF2...)
                int avtomatCount = 1;

                if (shieldGroups != null) {

                    // Для автоматического заполнения фаз, если не указана конкретная
                    int numberPhase = 1;

                    // Проход по группам
                    for (int j = 0; j < shieldGroups.size(); j++) {
                        // Получаем группу и записываем ее данные в таблицу, если имеется поле адрес
                        Group group = shieldGroups.get(j);
                        ////////////////////////////////////////////////////////////////////////

                        if (!group.getAddress().isEmpty()) {

                            row = sheetInsulation.createRow(countRow);

                            //увеличиваем высоту строки, чтобы вместить две строки текста
                            //row.setHeightInPoints((2*sheetInsulation.getDefaultRowHeightInPoints()));

                            // Столбец пункт
                            cell = row.createCell(0);
                            cell.setCellValue(paragraph++);
                            cell.setCellStyle(excelStyle.style);

                            // Столбец наименование линии
                            cell = row.createCell(1);
                            String lineName = "";
                            if (group.getDesignation().isEmpty()) {
                                lineName = avtomat + avtomatCount + " - " + group.getAddress();
                                avtomatCount++;
                            } else lineName = group.getDesignation() + " - " + group.getAddress();
                            cell.setCellValue(lineName);
                            cell.setCellStyle(excelStyle.style);

                            // Столбец Марка, кол-во жил, сечение
                            cell = row.createCell(2);
                            if (!group.getNumberOfWireCores().isEmpty()) {
                                String val = group.getCable() + " " + group.getNumberOfWireCores() + "x" + group.getWireThickness();
                                cell.setCellValue(val);
                            }

                            cell.setCellStyle(excelStyle.style);

                            // Столбец Напряжение мегаомметра
                            cell = row.createCell(3);
                            cell.setCellValue("2500");
                            cell.setCellStyle(excelStyle.style);

                            // Столбец Допустимое R
                            cell = row.createCell(4);
                            cell.setCellValue("0,5");
                            cell.setCellStyle(excelStyle.style);

                            // Столбцы измерений

                            // Автоматическое заполнение А,В,С, елси фаза не заполнена
                            if (group.getPhases().isEmpty() && !group.getAddress().isEmpty()){
                                switch (numberPhase){
                                    case 1: group.setPhases("А"); break;
                                    case 2: group.setPhases("В"); break;
                                    case 3: group.setPhases("С"); break;
                                }
                                if (numberPhase == 3) numberPhase = 1;
                                else numberPhase++;
                            }

                            for (int k = 5; k < 16; k++) {
                                cell = row.createCell(k);
                                cell.setCellValue("-");
                                cell.setCellStyle(excelStyle.style);
                            }

                            // Нужно ли заполнять N-PE
                            boolean isPE = group.getNumberOfWireCores().equals("3") || group.getNumberOfWireCores().equals("5");
                            // Если в изоляции везде прочерки, "соотв." не заполнится
                            boolean conformity = false;

                            // Столбцы сопротивления
                            switch (group.getPhases()) {
                                case "А":
                                    setInsulation1Phase(row, isPE,8);
                                    conformity = true;
                                    break;
                                case "В":
                                    setInsulation1Phase(row, isPE,9);
                                    conformity = true;
                                    break;
                                case "С":
                                    setInsulation1Phase(row, isPE,10);
                                    conformity = true;
                                    break;
                                case "АВС":
                                    setInsulation3Phase(row, isPE);
                                    conformity = true;
                                    break;
                            }
                            // Столбец соотв
                            if (conformity) row.getCell(15).setCellValue("соотв.");
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
                15, // конец столбца
                0, //начало строки
                countRow // конец строки
        );
////////////////////////////////////////////////////////////////////////////////////////////////////


        // Созданиие протокола петли
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////
        // Заполняем строки заказчик, объект, адрес, дата
        fillMainData(sheetF0, 14);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Заполняем строку погоды
        fillWeather(sheetF0, 10, 6);

        // Начинаем с 29 строки, первые 28 занимает шапка таблицы
         countRow = 28;
         paragraph = 1;

        if (shields != null) {
            // Проход по щитам
            for (int i = 0; i < shields.size(); i++) {
                // Получаем щит
                Shield shield = shields.get(i);
                row = sheetF0.createRow(countRow);
                Cell cell;

                // Объединяем столбцы для вставки названия щита
                sheetF0.addMergedRegion(new CellRangeAddress(
                        countRow, //first row (0-based)
                        countRow, //last row  (0-based)
                        0, //first column (0-based)
                        14  //last column  (0-based)
                ));

                // Вставляем название щита
                cell = row.createCell(0);
                ////////////////////////////////////////////////////////////////////////
                cell.setCellValue(shield.getName());
                cell.setCellStyle(excelStyle.style);

                countRow++;
                // Получаем группы щита
                ArrayList<Group> shieldGroups = shield.getShieldGroups();
                // Переменная для автоматической генерации номера автомата (QF1, QF2...)
                int avtomatCount = 1;
                if (shieldGroups != null) {

                    // Для автоматического заполнения фаз, если не указана конкретная
                    int numberPhase = 1;

                    // Проход по группам
                    for (int j = 0; j < shieldGroups.size(); j++) {
                        // Получаем группу и записываем ее данные в таблицу, если имеется поле адрес
                        Group group = shieldGroups.get(j);
                        ////////////////////////////////////////////////////////////////////////

                        if (!group.getAddress().isEmpty()) {

                            row = sheetF0.createRow(countRow);

                            //увеличиваем высоту строки, чтобы вместить две строки текста
                            row.setHeightInPoints((2*sheetF0.getDefaultRowHeightInPoints()));

                            // Столбец пункт
                            cell = row.createCell(0);
                            cell.setCellValue(paragraph++);
                            cell.setCellStyle(excelStyle.style);

                            // Столбец наименование линии
                            cell = row.createCell(1);
                            String lineName = "";
                            if (group.getDesignation().isEmpty()) {
                                lineName = avtomat + avtomatCount + " - " + group.getAddress();
                                avtomatCount++;
                            } else lineName = group.getDesignation() + " - " + group.getAddress();
                            cell.setCellValue(lineName);
                            cell.setCellStyle(excelStyle.style);

                            // Типовое обозначение автомата
                            cell = row.createCell(2);
                            cell.setCellValue(group.getMachineBrand());
                            cell.setCellStyle(excelStyle.style);

                            // Столбец Тип расцепителя
                            cell = row.createCell(3);
                            cell.setCellValue(group.getReleaseType());
                            cell.setCellStyle(excelStyle.style);

                            // Столбец Ном.ток
                            cell = row.createCell(4);
                            cell.setCellValue(group.getRatedCurrent());
                            cell.setCellStyle(excelStyle.style);

                            // Создаем столбцы измерений и ставим туда "-"
                            for (int k = 6; k < 15; k++) {
                                cell = row.createCell(k);
                                cell.setCellValue("-");
                                cell.setCellStyle(excelStyle.style);
                            }

                            // Столбец Диапазон тока срабатывания расцепителя
                            cell = row.createCell(5);
                            cell.setCellFormula(ExcelFormula.getRange(countRow));
                            cell.setCellStyle(excelStyle.style);

                            // Столбцы измерений

                            // Автоматическое заполнение А,В,С, елси фаза не заполнена
                            if (group.getPhases().isEmpty() && !group.getAddress().isEmpty()){
                                switch (numberPhase){
                                    case 1: group.setPhases("А"); break;
                                    case 2: group.setPhases("В"); break;
                                    case 3: group.setPhases("С"); break;
                                }
                                if (numberPhase == 3) numberPhase = 1;
                                else numberPhase++;
                            }

                            // Если в изоляции везде прочерки, "соотв." не заполнится
                            boolean conformity = false;

                            switch (group.getPhases()) {
                                case "А":
                                    cell = row.createCell(9);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(excelStyle.style);

                                    cell = row.createCell(6);
                                    cell.setCellFormula(ExcelFormula.getComputeResistA(countRow));
                                    cell.setCellStyle(excelStyle.style);

                                    conformity = true;
                                    break;
                                case "В":
                                    cell = row.createCell(10);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(excelStyle.style);

                                    cell = row.createCell(7);
                                    cell.setCellFormula(ExcelFormula.getComputeResistB(countRow));
                                    cell.setCellStyle(excelStyle.style);

                                    conformity = true;
                                    break;
                                case "С":
                                    cell = row.createCell(11);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(excelStyle.style);

                                    cell = row.createCell(8);
                                    cell.setCellFormula(ExcelFormula.getComputeResistC(countRow));
                                    cell.setCellStyle(excelStyle.style);

                                    conformity = true;
                                    break;
                                case "АВС":
                                    cell = row.createCell(9);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(excelStyle.style);

                                    cell = row.createCell(6);
                                    cell.setCellFormula(ExcelFormula.getComputeResistA(countRow));
                                    cell.setCellStyle(excelStyle.style);

                                    cell = row.createCell(10);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(excelStyle.style);

                                    cell = row.createCell(7);
                                    cell.setCellFormula(ExcelFormula.getComputeResistB(countRow));
                                    cell.setCellStyle(excelStyle.style);

                                    cell = row.createCell(11);
                                    cell.setCellFormula(ExcelFormula.getRandomCurrent(group.getRatedCurrent()));
                                    cell.setCellStyle(excelStyle.style);

                                    cell = row.createCell(8);
                                    cell.setCellFormula(ExcelFormula.getComputeResistC(countRow));
                                    cell.setCellStyle(excelStyle.style);

                                    conformity = true;
                                    break;
                            }

                            // Столбец доп время срабатывания. аппарата защиты
                            cell = row.createCell(12);
                            cell.setCellValue("0,4");
                            cell.setCellStyle(excelStyle.style);

                            // Столбец  время срабатывания. аппарата защиты по времятоковой
                            cell = row.createCell(13);
                            cell.setCellValue("< 0,4");
                            cell.setCellStyle(excelStyle.style);

                            // Столбец соотв
                            if (conformity) row.getCell(14).setCellValue("соотв.");

                            countRow++;
                        }
                    }
                }
            }
        }


        //устанавливаем область печати
        wb.setPrintArea(
                ExcelData.indexF0Sheet, // индекс листа
                0, // начало столбца
                14, // конец столбца
                0, //начать строку
                countRow // конец строки
        );
////////////////////////////////////////////////////////////////////////////////////////////////////


        // Удаляем ненужные протоколы
        if (!isF0)wb.removeSheetAt(wb.getSheetIndex(sheetF0));
        if (!isInsulation)wb.removeSheetAt(wb.getSheetIndex(sheetInsulation));


// Создание файла
////////////////////////////////////////////////////////////////////////////////////////////////////
        try (FileOutputStream fileOut = new FileOutputStream(getExternalPath())) {
            wb.write(fileOut);
        }
        wb.close();

    }

    private void setInsulation1Phase(Row row, boolean isPE, int i){

        Cell cell = row.createCell(i);
        cell.setCellFormula(ExcelFormula.randomInsulation);
        cell.setCellStyle(excelStyle.style);

        if (isPE) {
            cell = row.createCell(i+3);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(excelStyle.style);

            cell = row.createCell(14);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(excelStyle.style);

        }
    }

    private void setInsulation3Phase(Row row, boolean isPE){
        for (int i = 5; i < 8; i++) {
            Cell cell = row.createCell(i);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(excelStyle.style);

            cell = row.createCell(i+3);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(excelStyle.style);
            if (isPE) {
                cell = row.createCell(i+6);
                cell.setCellFormula(ExcelFormula.randomInsulation);
                cell.setCellStyle(excelStyle.style);
            }
        }
        if (isPE) {
            Cell cell = row.createCell(14);
            cell.setCellFormula(ExcelFormula.randomInsulation);
            cell.setCellStyle(excelStyle.style);
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private File getExternalPath() {
        return new File(context.getExternalFilesDir(null), fileName);
    }

    private void fillMainData(Sheet sheet, int column){
        Row row1 = sheet.getRow(0);
        Row row2 = sheet.getRow(1);
        Row row3 = sheet.getRow(2);
        Row row4 = sheet.getRow(3);

        Cell cell_1_15 = row1.createCell(column);
        Cell cell_2_15 = row2.createCell(column);
        Cell cell_3_15 = row3.createCell(column);
        Cell cell_4_15 = row4.createCell(column);

        cell_1_15.setCellValue("Заказчик: " + report.getCustomer());
        cell_2_15.setCellValue("Объект: " + report.getObject());
        cell_3_15.setCellValue("Адрес: " + report.getAddress());
        cell_4_15.setCellValue("Дата: " + report.getDate());

        cell_1_15.setCellStyle(excelStyle.styleBorderNoneRight);
        cell_2_15.setCellStyle(excelStyle.styleBorderNoneRight);
        cell_3_15.setCellStyle(excelStyle.styleBorderNoneRight);
        cell_4_15.setCellStyle(excelStyle.styleBorderNoneRight);
    }

    private void fillWeather(Sheet sheet,int row, int column){
        Row r = sheet.getRow(row);
        Cell c = r.createCell(column);
        String data = "Температура воздуха " +
                report.getTemperature() +
                " °С.  Влажность воздуха " +
                report.getHumidity() +
                "%.  Атмосферное давление " +
                report.getPressure() +
                "  мм.рт.ст.";
        c.setCellValue(data);
        c.setCellStyle(excelStyle.styleBorderNoneCenter);
    }

    public void setNecessaryProtocols(){
        Set<TypeOfWork> type_of_work = report.getType_of_work();

        if (type_of_work != null) {
            isInsulation = type_of_work.contains(TypeOfWork.Insulation);
            isF0 = type_of_work.contains(TypeOfWork.PhaseZero);
            isGround = type_of_work.contains(TypeOfWork.Grounding);
            isMetallicBond = type_of_work.contains(TypeOfWork.MetallicBond);
            isUzo = type_of_work.contains(TypeOfWork.Uzo);
            isVizual = type_of_work.contains(TypeOfWork.Visual);
        }

    }

}
