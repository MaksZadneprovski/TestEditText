package com.example.testedittext.report_creator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.example.testedittext.R;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.enums.TypeOfWork;
import com.example.testedittext.utils.ExcelData;
import com.example.testedittext.utils.MusicPlayer;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Report {

    Context context;
    private  String fileName;
    ReportEntity report;

    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";

    private boolean isF0, isInsulation, isGround, isUzo, isMetallicBond, isVizual, isAvtomat;

    public Report(Context context, String fileName, ReportEntity report) {
        this.context = context;
        this.fileName = fileName;
        this.report = report;
    }

    public boolean isBasicInfNorm(){
        if (report.getCustomer()!=null &&
                report.getName()!=null &&
                report.getAddress()!=null &&
                report.getCharacteristic()!=null &&
                report.getDate()!=null &&
                report.getHumidity()!=null &&
                report.getNumberReport()!=null &&
                report.getObject()!=null &&
                report.getPressure()!=null &&
                report.getTemperature()!=null &&
                report.getTest_type()!=null
        ){
            return !report.getCustomer().isEmpty() &&
                    !report.getName().isEmpty() &&
                    !report.getAddress().isEmpty() &&
                    !report.getCharacteristic().isEmpty() &&
                    !report.getDate().isEmpty() &&
                    !report.getHumidity().isEmpty() &&
                    !report.getNumberReport().isEmpty() &&
                    !report.getObject().isEmpty() &&
                    !report.getPressure().isEmpty() &&
                    !report.getTemperature().isEmpty() &&
                    !report.getTest_type().isEmpty();
        }
        return false;
    }

    public boolean isDataFromSettingsNorm(){
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, context.MODE_PRIVATE);
        String ingener = sharedPreferences.getString("ingener", "");
        String rukovoditel = sharedPreferences.getString("rukovoditel", "");
        return !ingener.isEmpty() && !rukovoditel.isEmpty();
    }


    public void generateReport() throws IOException {

        MusicPlayer musicPlayer = new MusicPlayer(context);
        musicPlayer.play();


        Workbook wb;
        wb = WorkbookFactory.create(context.getResources().openRawResource(R.raw.report3));

        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, context.MODE_PRIVATE);
        String ingener = sharedPreferences.getString("ingener", "");
        String ingener2 = sharedPreferences.getString("ingener2", "");
        String rukovoditel = sharedPreferences.getString("rukovoditel", "");

        String pr_num_attes_s = sharedPreferences.getString("numberSvid", "");
        String pr_organ_s = sharedPreferences.getString("organ", "");
        String pr_range_s = sharedPreferences.getString("range", "");
        String pr_date_pos_s = sharedPreferences.getString("lastDate", "");
        String pr_date_ocher_s = sharedPreferences.getString("nextDate", "");
        String pr_class_toch_s = sharedPreferences.getString("class_toch", "");
        String pr_zav_num_s = sharedPreferences.getString("numberZav", "");
        String pr_type_s = sharedPreferences.getString("type", ""   );

        Map<String, String> param = new HashMap<>();
        param.put("ingener", ingener);
        param.put("ingener2", ingener2);
        param.put("rukovoditel", rukovoditel);

        param.put("numberSvid", pr_num_attes_s);
        param.put("organ", pr_organ_s);
        param.put("range", pr_range_s);
        param.put("lastDate", pr_date_pos_s);
        param.put("nextDate", pr_date_ocher_s);
        param.put("class_toch", pr_class_toch_s);
        param.put("numberZav", pr_zav_num_s);
        param.put("type", pr_type_s);

        // Для нумерации протоколов
        ArrayList<Sheet> sheets = new ArrayList<>();
        
        Sheet sheetProgram = wb.getSheet("Program");
        Sheet sheetVO = wb.getSheet("VO");
        Sheet sheetInsulation = wb.getSheet("Insulation");
        Sheet sheetF0 = wb.getSheet("F0");
        Sheet sheetMS = wb.getSheet("MS");
        Sheet sheetUzo = wb.getSheet("Uzo");
        Sheet sheetAvtomat = wb.getSheet("Avtomat");
        Sheet sheetGround = wb.getSheet("Ground");
        Sheet sheetDefects = wb.getSheet("Vedomost");
        Sheet sheetZakl = wb.getSheet("Zakluchenie");

        sheets.add(sheetProgram);
        sheets.add(sheetVO);
        sheets.add(sheetInsulation);
        sheets.add(sheetF0);
        sheets.add(sheetMS);
        sheets.add(sheetUzo);
        sheets.add(sheetAvtomat);
        sheets.add(sheetGround);
        sheets.add(sheetDefects);
        sheets.add(sheetZakl);

        TitulReport.generateTitul(wb, report, param);

        // Определяем необходимость тех или иных протоколов
        setNecessaryProtocols();

        ProgramReport.generateProgram(wb, report, param);

        int protocolNumber = 1;

        // Удаляем ненужные протоколы
        if (isVizual) {
            // Присваиваем номер протоколу
            ExcelData.numberVOProtocol = protocolNumber++;
            wb = VOReport.generateVO(wb, report, param);
        }
        else wb.removeSheetAt(wb.getSheetIndex(sheetVO));

        if (isMetallicBond){
            // Присваиваем номер протоколу
            ExcelData.numberMSProtocol = protocolNumber++;
            wb = MSReport.generateMS(wb, report, param);
        }
        else wb.removeSheetAt(wb.getSheetIndex(sheetMS));

        if (isInsulation) {
            // Присваиваем номер протоколу
            ExcelData.numberInsulationProtocol = protocolNumber++;
            wb = InsulationReport.generateInsulation(wb, report, param);
        }
        else wb.removeSheetAt(wb.getSheetIndex(sheetInsulation));

        if (isF0) {
            // Присваиваем номер протоколу
            ExcelData.numberF0Protocol = protocolNumber++;
            wb = F0Report.generateF0(wb, report,  param);
        }
        else wb.removeSheetAt(wb.getSheetIndex(sheetF0));


        if (isUzo) {
            // Присваиваем номер протоколу
            ExcelData.numberUzoProtocol = protocolNumber++;
            wb = UzoReport.generateUzo(wb, report, param);
        }
        else wb.removeSheetAt(wb.getSheetIndex(sheetUzo));

        if (isAvtomat){
            // Присваиваем номер протоколу
            ExcelData.numberAvtomatProtocol = protocolNumber++;
            wb = AvtomatReport.generateAvtomat(wb, report, param);
        }
        else wb.removeSheetAt(wb.getSheetIndex(sheetAvtomat));

        if (isGround){
            // Присваиваем номер протоколу
            ExcelData.numberGroundingProtocol = protocolNumber;
            wb = GroundReport.generateGround(wb, report, param);
        }
        else wb.removeSheetAt(wb.getSheetIndex(sheetGround));

        DefectsReport.generateDefects(wb, report, param);
        Zakluchenie.generateZakl(wb, report, param);

        // Содержание в конце, чтобы посчитать сначала сколько страниц
        ContentReport.generateContent(wb,report, param);

        // Вставляем нумерацию страниц
        //insertNumeration(sheets);

        // Создание файла
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        try (FileOutputStream fileOut = new FileOutputStream(getExternalPath())) {
            wb.write(fileOut);
        }

        wb.close();


    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private File getExternalPath() {
        return new File(context.getExternalFilesDir(null), fileName);
    }

    // Сохранять в загрузки
//    private File getExternalPath() {
//        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//        return new File(downloadsDir, fileName);
//    }
    
    private void insertNumeration(ArrayList<Sheet> sheets){
        for (Sheet sheet : sheets) {
            Footer footer = sheet.getFooter();
            footer.setRight( "Страница " + HeaderFooter.page() + " из " + HeaderFooter.numPages() );
        }
    }

    static int fillRekvizity(int countRow, Sheet sheet, Workbook wb, Map<String, String> param, int i1, int i2,int i3){

        Font font11 = wb.createFont();
        font11.setFontHeightInPoints((short)11);
        font11.setFontName("Times New Roman");

        Font fontForSurname = wb.createFont();
        fontForSurname.setFontHeightInPoints((short)11);
        fontForSurname.setFontName("Times New Roman");
        fontForSurname.setUnderline((byte) 1);

        CellStyle style5;
        style5 = wb.createCellStyle();
        style5.setAlignment(HorizontalAlignment.LEFT);
        style5.setFont(font11);

        CellStyle style5Center;
        style5Center = wb.createCellStyle();
        style5Center.setAlignment(HorizontalAlignment.CENTER);
        style5Center.setFont(font11);

        CellStyle styleForSurname;
        styleForSurname = wb.createCellStyle();
        styleForSurname.setAlignment(HorizontalAlignment.LEFT);
        styleForSurname.setFont(fontForSurname);

        Row row = sheet.createRow(countRow);
        Cell cell = row.createCell(i1);
        cell.setCellValue("Испытания провели:");
        cell.setCellStyle(styleForSurname);

        countRow+=2;
        row = sheet.createRow(countRow);

        cell = row.createCell(i1);
        cell.setCellValue("Инженер");
        cell.setCellStyle(styleForSurname);
        cell = row.createCell(i2);
        cell.setCellValue("____________");
        cell.setCellStyle(style5Center);
        cell = row.createCell(i3);
        cell.setCellValue(param.get("ingener"));
        cell.setCellStyle(styleForSurname);

        countRow+=1;
        row = sheet.createRow(countRow);
        fillDescription(row, i1, i2, i3, style5, style5Center);





        if (!param.get("ingener2").isEmpty()){
            countRow += 2;
            row = sheet.createRow(countRow);
            cell = row.createCell(i1);
            cell.setCellValue("Инженер");
            cell.setCellStyle(styleForSurname);
            cell = row.createCell(i2);
            cell.setCellValue("____________");
            cell.setCellStyle(style5Center);
            cell = row.createCell(i3);
            cell.setCellValue(param.get("ingener2"));
            cell.setCellStyle(styleForSurname);

            countRow+=1;
            row = sheet.createRow(countRow);
            fillDescription(row, i1, i2, i3, style5, style5Center);

        }


        countRow += 2;
        row = sheet.createRow(countRow);
        cell = row.createCell(i1);
        cell.setCellValue("Протокол проверил:");
        cell.setCellStyle(styleForSurname);

        countRow+=2;

        row = sheet.createRow(countRow);

        cell = row.createCell(i1);
        cell.setCellValue("Руководитель  лаборатории");
        cell.setCellStyle(styleForSurname);
        cell = row.createCell(i2);
        cell.setCellValue("____________");
        cell.setCellStyle(style5Center);
        cell = row.createCell(i3);
        cell.setCellValue(param.get("rukovoditel"));
        cell.setCellStyle(styleForSurname);

        countRow+=1;
        row = sheet.createRow(countRow);
        fillDescription(row, i1, i2, i3, style5, style5Center);

        return countRow;
    }

    static  void fillDescription(Row row, int i1, int i2, int i3, CellStyle style5, CellStyle style5Center){
        Cell cell = row.createCell(i1);
        cell.setCellValue("(должность)");
        cell.setCellStyle(style5);
        cell = row.createCell(i2);
        cell.setCellValue("(подпись)");
        cell.setCellStyle(style5Center);
        cell = row.createCell(i3);
        cell.setCellValue("(ФИО)");
        cell.setCellStyle(style5);
    }

    public static void fillMainData(Sheet sheet, int column, ReportEntity report, Workbook wb){

        // Create a new font and alter it.
        Font font10 = wb.createFont();
        font10.setFontHeightInPoints((short)10);
        font10.setFontName("Times New Roman");
        font10.setBold(false);

        // Создаем стиль для данных об объекте
        CellStyle styleBorderNoneRight = wb.getCellStyleAt(7);
        styleBorderNoneRight.setBorderTop(BorderStyle.NONE);
        styleBorderNoneRight.setBorderBottom(BorderStyle.NONE);
        styleBorderNoneRight.setBorderLeft(BorderStyle.NONE);
        styleBorderNoneRight.setBorderRight(BorderStyle.NONE);
        styleBorderNoneRight.setWrapText(false);
        styleBorderNoneRight.setFont(font10);
        styleBorderNoneRight.setAlignment(HorizontalAlignment.RIGHT);
        styleBorderNoneRight.setVerticalAlignment(VerticalAlignment.CENTER);

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

        cell_1_15.setCellStyle(styleBorderNoneRight);
        cell_2_15.setCellStyle(styleBorderNoneRight);
        cell_3_15.setCellStyle(styleBorderNoneRight);
        cell_4_15.setCellStyle(styleBorderNoneRight);
    }

    public static void fillWeather(Sheet sheet,int row, ReportEntity report, Workbook wb){

        // Create a new font and alter it.
        Font font10 = wb.createFont();
        font10.setFontHeightInPoints((short)10);
        font10.setFontName("Times New Roman");
        font10.setBold(false);

        CellStyle styleBorderNoneCenter;
        // Создаем стиль для данных об объекте
        styleBorderNoneCenter = wb.createCellStyle();
        styleBorderNoneCenter.setBorderTop(BorderStyle.NONE);
        styleBorderNoneCenter.setBorderBottom(BorderStyle.NONE);
        styleBorderNoneCenter.setBorderLeft(BorderStyle.NONE);
        styleBorderNoneCenter.setBorderRight(BorderStyle.NONE);
        styleBorderNoneCenter.setWrapText(false);
        styleBorderNoneCenter.setFont(font10);
        styleBorderNoneCenter.setAlignment(HorizontalAlignment.CENTER);
        styleBorderNoneCenter.setVerticalAlignment(VerticalAlignment.CENTER);

        Row r = sheet.getRow(row);
        Cell c = r.createCell(0);
        String data = "Температура воздуха " +
                report.getTemperature() +
                " °С.  Влажность воздуха " +
                report.getHumidity() +
                "%.  Атмосферное давление " +
                report.getPressure() +
                "  мм.рт.ст.";
        c.setCellValue(data);
        c.setCellStyle(styleBorderNoneCenter);
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
            isAvtomat = type_of_work.contains(TypeOfWork.Avtomat);
        }

    }

}
