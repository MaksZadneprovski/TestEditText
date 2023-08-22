package com.example.testedittext.report_creator;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.enums.TypeOfWork;

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
import java.util.Set;

public class ProgramReport {

    private static  Sheet sheetProgram;
    private static  Workbook wBook;
    private static  int countRow ;
    static final float number_of_characters_per_line = 20.0F;

    public static Workbook generateProgram(Workbook wb, ReportEntity report, Map<String, String> param) {
        sheetProgram = wb.getSheet("Program");
        wBook = wb;
        countRow = 13;

        // Заполняем строки заказчик, объект, адрес, дата
        Report.fillMainData(sheetProgram, 4,number_of_characters_per_line, report, wb);


        Set<TypeOfWork> type_of_work = report.getType_of_work();

        // Визуальный осмотр
        if (type_of_work.contains(TypeOfWork.Visual)){

            ArrayList<String> list = new ArrayList<>();
            list.add("Визуальный осмотр");
            list.add("Электроустановка здания");
            list.add("Проектная документация и осмотр электроустановки");
            list.add("ГОСТ Р 50571 (1-28)");

            fillProgram(list);

        }

        // Метсвязь
        if (type_of_work.contains(TypeOfWork.MetallicBond)){
            ArrayList<String> list = new ArrayList<>();
            list.add("Проверка  наличия  цепи  между  заземлителем  и  заземленными  элементами  оборудования");
            list.add("Электрооборудование");
            list.add("Переходное сопротивление контакта");
            list.add("ГОСТ Р 50571.5.54-2013, ПУЭ 1.8.39 п.2.");

            fillProgram(list);

        }

        // Изоляция
        if (type_of_work.contains(TypeOfWork.Insulation)){
            ArrayList<String> list = new ArrayList<>();
            list.add("Измерение  сопротивления  изоляции  проводов и  кабелей");
            list.add("Вводные и отходящие линии, групповые электросети");
            list.add("Сопротивление изоляции");
            list.add("ПУЭ п.1.8.37; ГОСТ Р 50571.16-2019");

            fillProgram(list);
        }

        // Петля
        if (type_of_work.contains(TypeOfWork.PhaseZero)){
            ArrayList<String> list = new ArrayList<>();
            list.add("Проверка  срабатывания  защиты  с  измерением  сопротивления  петли  “фаза-нуль” и   возможного  тока короткого  замыкания");
            list.add("Электрооборудование");
            list.add("Сопротивление петли «фаза-нуль»");
            list.add("ПУЭ п.1.7.79; ПУЭ: п.1.8.39; ГОСТ Р 50571.4.43-2012; ГОСТ Р 50571.5.54-2013");

            fillProgram(list);
        }

        // Заземляющее устройство
        if (type_of_work.contains(TypeOfWork.Grounding)){
            ArrayList<String> list = new ArrayList<>();
            list.add("Измерение сопротивления заземлителей и заземляющих устройств");
            list.add("Заземляющее устройство");
            list.add("Сопротивление заземляющего устройства");
            list.add("ПУЭ: 1.7.100-1.7.102, ПУЭ: 1.8.39;\n" +
                    "ПУЭ: 1.7.61; ГОСТ Р 50571.5.54-2013; РД 34-21.122-87, СО153-34.21.122-2003 «Инструкция по устройству молниезащиты зданий и сооружений».");

            fillProgram(list);
        }

        // УЗО
        if (type_of_work.contains(TypeOfWork.Uzo)){
            ArrayList<String> list = new ArrayList<>();
            list.add("Проверка и испытания устройств защитного отключения, управляемых дифференциальным током (УЗО)");
            list.add("Электрооборудование");
            list.add("Проверка расцепителя автоматического выключателя");
            list.add("ГОСТ IEC 61009-1-2014");

            fillProgram(list);
        }

        // Прогрузка
        if (type_of_work.contains(TypeOfWork.Avtomat)){
            ArrayList<String> list = new ArrayList<>();
            list.add("Проверка автоматических выключателей напряжением до 1000в");
            list.add("Электрооборудование");
            list.add("Проверка расцепителя автоматического выключателя");
            list.add("ПУЭ: 1.8.37 п.3; ПУЭ: 3.1.8; ПУЭ: 1.7.79; ПУЭ: 7.3.139; ПУЭ: 1.8.34. п.3.; ГОСТ IEC 60898-1-2020");

            fillProgram(list);
        }

        Row row;
        Cell cell;

        Font font12 = wb.createFont();
        font12.setFontHeightInPoints((short)12);
        font12.setFontName("Times New Roman");
        font12.setUnderline(Font.U_SINGLE);

        CellStyle style3;
        style3 = wb.createCellStyle();
        style3.setFont(font12);
        style3.setAlignment(HorizontalAlignment.CENTER);
        style3.setWrapText(false);

        // Руководитель
        row = sheetProgram.getRow(22);
        cell = row.createCell(4);
        cell.setCellValue(param.get("rukovoditel"));
        cell.setCellStyle(style3);

        //устанавливаем область печати
        wb.setPrintArea(
                wb.getSheetIndex(sheetProgram), // индекс листа
                0, // начало столбца
                5, // конец столбца
                0, //начало строки
                50 // конец строки
        );
        return wb;
    }

    private static void fillProgram(ArrayList<String> list){
        Row row;
        Cell cell;

        // Высота строки
        int strokeHeigth = 5;

        Font font = wBook.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("Times New Roman");

        CellStyle style;
        style = wBook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle style2;
        style2 = wBook.createCellStyle();
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style2.setBorderTop(BorderStyle.THIN);
        style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        style2.setWrapText(true);
        style2.setFont(font);



        row = sheetProgram.createRow(countRow);

        for (int i = 1; i < 4; i++) {
            cell = row.createCell(i);
            cell.setCellValue(list.get(i-1));
            cell.setCellStyle(style);
        }

        cell = row.createCell(4);
        cell.setCellValue(list.get(3));
        cell.setCellStyle(style2);

        //увеличиваем высоту строки, чтобы вместить две строки текста
        row.setHeightInPoints((strokeHeigth * sheetProgram.getDefaultRowHeightInPoints()));
        countRow++;
    }
}
