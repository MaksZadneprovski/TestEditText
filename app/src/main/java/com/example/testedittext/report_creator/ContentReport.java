package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.enums.TypeOfWork;
import com.example.testedittext.utils.Excel;
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
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Map;
import java.util.Set;

public class ContentReport {

    static int countRow;
    static Row row;
    static Cell cell;

    // Высота строки
    static int strokeHeigth = 3;

    public static Workbook generateContent(Workbook wb, ReportEntity report, Map<String, String> param) {

        countRow = 13;
        // Обнуляем страницы, так как поле статичное и если создавать несколько отчетов, то пизда
        Storage.pagesCountTotal = 3;

        Sheet sheetContent = wb.getSheet("Soderzh");

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetContent, 9, report, wb);

        String text = "ПРОТОКОЛ № ";



        Font font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        font.setFontName("Times New Roman");
        font.setUnderline(Font.U_NONE);
        font.setBold(true);

        Font font12 = wb.createFont();
        font12.setFontHeightInPoints((short)12);
        font12.setFontName("Times New Roman");
        font12.setUnderline(Font.U_SINGLE);

        Font font18 = wb.createFont();
        font12.setFontHeightInPoints((short)18);
        font12.setFontName("Times New Roman");


        Font fontForSurname = wb.createFont();
        fontForSurname.setFontHeightInPoints((short)11);
        fontForSurname.setFontName("Times New Roman");
        fontForSurname.setUnderline((byte) 1);

        CellStyle styleForSurname;
        styleForSurname = wb.createCellStyle();
        styleForSurname.setAlignment(HorizontalAlignment.LEFT);
        styleForSurname.setFont(fontForSurname);

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
        style2.setFont(font18);
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle style3;
        style3 = wb.createCellStyle();
        style3.setFont(font12);
        style3.setAlignment(HorizontalAlignment.CENTER);
        style3.setWrapText(false);


        Set<TypeOfWork> type_of_work = report.getType_of_work();


        // Программа испытаний
        generateStroke(sheetContent, 1,style,style2, "Программа испытаний");

        // Визуальный осмотр
        if (type_of_work!=null) {
            if (type_of_work.contains(TypeOfWork.Visual)) {
                generateStroke(sheetContent, 1,style,style2, text + Excel.numberVOProtocol + " Визуального осмотра" );
            }

            // Метсвязь
            if (type_of_work.contains(TypeOfWork.MetallicBond)) {
                generateStroke(sheetContent, Storage.pagesCountMS,style,style2, text + Excel.numberMSProtocol + " Проверки наличия цепи между заземленными установками и элементами заземленной установки" );
            }

            // Изоляция
            if (type_of_work.contains(TypeOfWork.Insulation)) {
                generateStroke(sheetContent, Storage.pagesCountInsulation,style,style2, text + Excel.numberInsulationProtocol + " Проверки сопротивления изоляции проводов, кабелей и обмоток электрических машин" );
            }

            // Петля
            if (type_of_work.contains(TypeOfWork.PhaseZero)) {
                generateStroke(sheetContent, Storage.pagesCountF0,style,style2, text + Excel.numberF0Protocol + " Проверки согласования параметров цепи «фаза – нуль» с характеристиками аппаратов  защиты и непрерывности защитных проводников" );
            }

            // УЗО
            if (type_of_work.contains(TypeOfWork.Uzo)) {
                generateStroke(sheetContent, Storage.pagesCountUzo,style,style2, text + Excel.numberUzoProtocol + " Проверки работы устройства защитного отключения (УЗО)" );
            }

            // Прогрузка
            if (type_of_work.contains(TypeOfWork.Avtomat)) {
                generateStroke(sheetContent, Storage.pagesCountAvtomat,style,style2, text + Excel.numberAvtomatProtocol + " Проверка действия расцепителей автоматических выключателей до 1000 В" );
            }

            // Заземление
            if (type_of_work.contains(TypeOfWork.Grounding)) {
                generateStroke(sheetContent, Storage.pagesCountGround,style,style2, text + Excel.numberGroundingProtocol + " Проверка сопротивлений заземлителей и заземляющих устройств" );
            }
        }

        // Ведомость  дефектов
        generateStroke(sheetContent, 1,style,style2, "Ведомость  дефектов" );

        // Заключение
        generateStroke(sheetContent, 1,style,style2, "Заключение" );

        // Свидетельства
        generateStroke(sheetContent, 2,style,style2, "Свидетельства" );


        // Руководитель
        row = sheetContent.getRow(57);
        cell = row.createCell(8);
        cell.setCellValue(param.get("rukovoditel"));
        cell.setCellStyle(style3);

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

    static void generateStroke(Sheet sheet, int pagesCount, CellStyle style, CellStyle style2, String text){

        row = sheet.createRow(countRow);
        cell = row.createCell(0);
        cell.setCellValue(text);
        cell.setCellStyle(style);

        // Пробегаемся по ячейкам в строке, которые будут объединены, дабы их создать и не ловить налпоинтер
        for (int i = 1; i < 9; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
        }

        // Объединяем столбцы в строке
        sheet.addMergedRegion(new CellRangeAddress(
                countRow, //first row (0-based)
                countRow, //last row  (0-based)
                0, //first column (0-based)
                8  //last column  (0-based)
        ));

        // Создаем ячейку, чтобы вставить номер страницы
        cell = row.createCell(9);

        //Вставляем номер страницы, с которой начинается протокол. То есть сумма предыдущих страниц
        cell.setCellValue(Storage.pagesCountTotal);

        // Плюсуем к сумме страниц, кол-во стр. в этом протоколе
        Storage.pagesCountTotal += pagesCount;

        cell.setCellStyle(style2);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((strokeHeigth * sheet.getDefaultRowHeightInPoints()));

        countRow++;
    }
}
