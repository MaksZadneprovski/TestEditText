package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.enums.TypeOfWork;
import com.example.testedittext.utils.ExcelData;

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

import java.util.Set;

public class ContentReport {



    public static Workbook generateVO (Workbook wb, ReportEntity report) {
        Sheet sheetContent = wb.getSheet("Soderzh");

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetContent, 9, report, wb);


        Row row;
        Cell cell;

        // Высота строки
        int strokeHeigth = 3;

        int protocolNumber = 1;
        int countRow = 13;
        String text = "ПРОТОКОЛ № ";



        Font font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        font.setFontName("Times New Roman");
        font.setUnderline(Font.U_NONE);
        font.setBold(true);

        CellStyle style;
        style = wb.createCellStyle();
        style.setWrapText(true);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        //style.setBorderRight(BorderStyle.THIN);
        //style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle style2;
        style2 = wb.createCellStyle();
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style2.setBorderTop(BorderStyle.THIN);
        style2.setTopBorderColor(IndexedColors.BLACK.getIndex());



        Set<TypeOfWork> type_of_work = report.getType_of_work();

        // Список прилагаемой технической документации



        row = sheetContent.createRow(countRow);
        cell = row.createCell(0);
        cell.setCellValue("Список прилагаемой технической документации");
        cell.setCellStyle(style);

        for (int i = 1; i < 9; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
        }

        // Объединяем столбцы для вставки названия щита
        sheetContent.addMergedRegion(new CellRangeAddress(
                countRow, //first row (0-based)
                countRow, //last row  (0-based)
                0, //first column (0-based)
                8  //last column  (0-based)
        ));

        cell = row.createCell(9);
        cell.setCellStyle(style2);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));



        countRow++;

        // Пояснительная  записка
        row = sheetContent.createRow(countRow);
        cell = row.createCell(0);
        cell.setCellValue("Пояснительная  записка");
        cell.setCellStyle(style);

        for (int i = 1; i < 9; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
        }

        // Объединяем столбцы для вставки названия щита
        sheetContent.addMergedRegion(new CellRangeAddress(
                countRow, //first row (0-based)
                countRow, //last row  (0-based)
                0, //first column (0-based)
                8  //last column  (0-based)
        ));

        cell = row.createCell(9);
        cell.setCellStyle(style2);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
        countRow++;

        // Программа испытаний
        row = sheetContent.createRow(countRow);
        cell = row.createCell(0);
        cell.setCellValue("Программа испытаний");
        cell.setCellStyle(style);

        for (int i = 1; i < 9; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
        }

        // Объединяем столбцы для вставки названия щита
        sheetContent.addMergedRegion(new CellRangeAddress(
                countRow, //first row (0-based)
                countRow, //last row  (0-based)
                0, //first column (0-based)
                8  //last column  (0-based)
        ));

        cell = row.createCell(9);
        cell.setCellStyle(style2);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
        countRow++;

        // Визуальный осмотр
        if (type_of_work.contains(TypeOfWork.Visual)){

            row = sheetContent.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(text + protocolNumber + " Визуального осмотра");
            cell.setCellStyle(style);
            // Присваиваем номер протоколу
            ExcelData.numberVOProtocol = protocolNumber;

            for (int i = 1; i < 9; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
            }

            // Объединяем столбцы для вставки названия щита
            sheetContent.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    8  //last column  (0-based)
            ));

            cell = row.createCell(9);
            cell.setCellStyle(style2);

            //увеличиваем высоту строки, чтобы вместить две строки текста
            row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
            countRow++;
            protocolNumber++;
        }

        // Метсвязь
        if (type_of_work.contains(TypeOfWork.Visual)){

            row = sheetContent.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(text + protocolNumber + " Проверки наличия цепи между заземленными установками и элементами заземленной установки");
            cell.setCellStyle(style);

            // Присваиваем номер протоколу
            ExcelData.numberMSProtocol = protocolNumber;

            for (int i = 1; i < 9; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
            }

            // Объединяем столбцы для вставки названия щита
            sheetContent.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    8  //last column  (0-based)
            ));

            cell = row.createCell(9);
            cell.setCellStyle(style2);

            //увеличиваем высоту строки, чтобы вместить две строки текста
            row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
            countRow++;
            protocolNumber++;
        }

        // Изоляция
        if (type_of_work.contains(TypeOfWork.Insulation)){
            row = sheetContent.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(text + protocolNumber + " Проверки сопротивления изоляции проводов, кабелей и обмоток электрических машин");
            cell.setCellStyle(style);

            // Присваиваем номер протоколу
            ExcelData.numberInsulationProtocol = protocolNumber;

            for (int i = 1; i < 9; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
            }

            // Объединяем столбцы для вставки названия щита
            sheetContent.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    8  //last column  (0-based)
            ));

            cell = row.createCell(9);
            cell.setCellStyle(style2);

            //увеличиваем высоту строки, чтобы вместить две строки текста
            row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
            countRow++;
            protocolNumber++;
        }

        // Петля
        if (type_of_work.contains(TypeOfWork.PhaseZero)){
            row = sheetContent.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(text + protocolNumber + " Проверки согласования параметров цепи «фаза – нуль» с характеристиками аппаратов  защиты и непрерывности защитных проводников");
            cell.setCellStyle(style);

            // Присваиваем номер протоколу
            ExcelData.numberF0Protocol = protocolNumber;

            for (int i = 1; i < 9; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
            }

            // Объединяем столбцы для вставки названия щита
            sheetContent.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    8  //last column  (0-based)
            ));

            cell = row.createCell(9);
            cell.setCellStyle(style2);

            //увеличиваем высоту строки, чтобы вместить две строки текста
            row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
            countRow++;
            protocolNumber++;
        }

        // Заземляющее устройство
        if (type_of_work.contains(TypeOfWork.Grounding)){
            row = sheetContent.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(text + protocolNumber + " Проверки сопротивлений заземлителей и заземляющих устройств");
            cell.setCellStyle(style);

            // Присваиваем номер протоколу
            ExcelData.numberGroundingProtocol = protocolNumber;

            for (int i = 1; i < 9; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
            }

            // Объединяем столбцы для вставки названия щита
            sheetContent.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    8  //last column  (0-based)
            ));

            cell = row.createCell(9);
            cell.setCellStyle(style2);

            //увеличиваем высоту строки, чтобы вместить две строки текста
            row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
            countRow++;
            protocolNumber++;
        }

        // УЗО
        if (type_of_work.contains(TypeOfWork.Uzo)){
            row = sheetContent.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(text + protocolNumber + " Проверки работы устройства защитного отключения (УЗО)");
            cell.setCellStyle(style);

            // Присваиваем номер протоколу
            ExcelData.numberUzoProtocol = protocolNumber;

            for (int i = 1; i < 9; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
            }

            // Объединяем столбцы для вставки названия щита
            sheetContent.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    8  //last column  (0-based)
            ));

            cell = row.createCell(9);
            cell.setCellStyle(style2);

            //увеличиваем высоту строки, чтобы вместить две строки текста
            row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
            countRow++;
            protocolNumber++;
        }

        // Прогрузка
        if (type_of_work.contains(TypeOfWork.Avtomat)){
            row = sheetContent.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(text + protocolNumber + " Проверка действия расцепителей автоматических выключателей до 1000 В");
            cell.setCellStyle(style);

            // Присваиваем номер протоколу
            ExcelData.numberAvtomatProtocol = protocolNumber;

            for (int i = 1; i < 9; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style);
            }

            // Объединяем столбцы для вставки названия щита
            sheetContent.addMergedRegion(new CellRangeAddress(
                    countRow, //first row (0-based)
                    countRow, //last row  (0-based)
                    0, //first column (0-based)
                    8  //last column  (0-based)
            ));

            cell = row.createCell(9);
            cell.setCellStyle(style2);

            //увеличиваем высоту строки, чтобы вместить две строки текста
            row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
            countRow++;
            protocolNumber++;
        }

        // Ведомость  дефектов
        row = sheetContent.createRow(countRow);
        cell = row.createCell(0);
        cell.setCellValue("Ведомость  дефектов");
        cell.setCellStyle(style);

        for (int i = 1; i < 9; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
        }

        // Объединяем столбцы для вставки названия щита
        sheetContent.addMergedRegion(new CellRangeAddress(
                countRow, //first row (0-based)
                countRow, //last row  (0-based)
                0, //first column (0-based)
                8  //last column  (0-based)
        ));

        cell = row.createCell(9);
        cell.setCellStyle(style2);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
        countRow++;

        // Заключение
        row = sheetContent.createRow(countRow);
        cell = row.createCell(0);
        cell.setCellValue("Заключение");
        cell.setCellStyle(style);

        for (int i = 1; i < 9; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
        }

        // Объединяем столбцы для вставки названия щита
        sheetContent.addMergedRegion(new CellRangeAddress(
                countRow, //first row (0-based)
                countRow, //last row  (0-based)
                0, //first column (0-based)
                8  //last column  (0-based)
        ));

        cell = row.createCell(9);
        cell.setCellStyle(style2);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
        countRow++;

        // Свидетельства
        row = sheetContent.createRow(countRow);
        cell = row.createCell(0);
        cell.setCellValue("Свидетельства");
        cell.setCellStyle(style);

        for (int i = 1; i < 9; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
        }

        // Объединяем столбцы для вставки названия щита
        sheetContent.addMergedRegion(new CellRangeAddress(
                countRow, //first row (0-based)
                countRow, //last row  (0-based)
                0, //first column (0-based)
                8  //last column  (0-based)
        ));

        cell = row.createCell(9);
        cell.setCellStyle(style2);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((strokeHeigth * sheetContent.getDefaultRowHeightInPoints()));
        countRow++;


        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetContent), // индекс листа
                0, // начало столбца
                9, // конец столбца
                0, //начало строки
                50 // конец строки
        );

        return wb;

    }
}
