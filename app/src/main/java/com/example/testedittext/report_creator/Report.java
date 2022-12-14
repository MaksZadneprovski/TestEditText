package com.example.testedittext.report_creator;

import android.content.Context;

import com.example.testedittext.R;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.enums.TypeOfWork;


import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


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

    public Report(Context context, String fileName, ReportEntity report) {
        this.context = context;
        this.fileName = fileName;
        this.report = report;
    }

    public void generateReport() throws IOException {

        Workbook wb;
        wb = WorkbookFactory.create(context.getResources().openRawResource(R.raw.report3));

        ArrayList<Sheet> sheets = new ArrayList<>();
        
        Sheet sheetProgram = wb.getSheet("Program");
        Sheet sheetVO = wb.getSheet("VO");
        Sheet sheetInsulation = wb.getSheet("Insulation");
        Sheet sheetF0 = wb.getSheet("F0");
        Sheet sheetMS = wb.getSheet("MS");
        Sheet sheetUzo = wb.getSheet("Uzo");

        sheets.add(sheetProgram);
        sheets.add(sheetVO);
        sheets.add(sheetInsulation);
        sheets.add(sheetF0);
        sheets.add(sheetMS);
        sheets.add(sheetUzo);

        TitulReport.generateVO(wb, report);
        ContentReport.generateVO(wb,report);

        // ???????????????????? ?????????????????????????? ?????? ?????? ???????? ????????????????????
        setNecessaryProtocols();

        ProgramReport.generateVO(wb, report);

        // ?????????????? ???????????????? ??????????????????
        if (isVizual) wb = VOReport.generateVO(wb, report);
        else wb.removeSheetAt(wb.getSheetIndex(sheetVO));

        if (isF0) wb = F0Report.generateF0(wb, report);
        else wb.removeSheetAt(wb.getSheetIndex(sheetF0));

        if (isInsulation) wb = InsulationReport.generateInsulation(wb, report);
        else wb.removeSheetAt(wb.getSheetIndex(sheetInsulation));

        if (isMetallicBond) wb = MSReport.generateMS(wb, report);
        else wb.removeSheetAt(wb.getSheetIndex(sheetMS));

        if (isUzo) wb = UzoReport.generateUzo(wb, report);
        else wb.removeSheetAt(wb.getSheetIndex(sheetUzo));

        // ?????????????????? ?????????????????? ??????????????
        insertNumeration(sheets);

        // ???????????????? ??????????
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
    
    private void insertNumeration(ArrayList<Sheet> sheets){
        for (Sheet sheet : sheets) {
            Footer footer = sheet.getFooter();
            footer.setRight( "?????????????? " + HeaderFooter.page() + " ???? " + HeaderFooter.numPages() );
        }
    }

    public static void fillMainData(Sheet sheet, int column, ReportEntity report, Workbook wb){

        // Create a new font and alter it.
        Font font10 = wb.createFont();
        font10.setFontHeightInPoints((short)10);
        font10.setFontName("Times New Roman");
        font10.setBold(false);

        // ?????????????? ?????????? ?????? ???????????? ???? ??????????????
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

        cell_1_15.setCellValue("????????????????: " + report.getCustomer());
        cell_2_15.setCellValue("????????????: " + report.getObject());
        cell_3_15.setCellValue("??????????: " + report.getAddress());
        cell_4_15.setCellValue("????????: " + report.getDate());

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
        // ?????????????? ?????????? ?????? ???????????? ???? ??????????????
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
        String data = "?????????????????????? ?????????????? " +
                report.getTemperature() +
                " ????.  ?????????????????? ?????????????? " +
                report.getHumidity() +
                "%.  ?????????????????????? ???????????????? " +
                report.getPressure() +
                "  ????.????.????.";
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
        }

    }

}
