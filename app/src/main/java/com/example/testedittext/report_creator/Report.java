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


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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

    public Report(Context context, String fileName, ReportEntity report) {
        this.context = context;
        this.fileName = fileName;
        this.report = report;
    }

    public void generateReport() throws IOException {

        Workbook wb;
        wb = WorkbookFactory.create(context.getResources().openRawResource(R.raw.report3));

        Sheet sheetVO = wb.getSheet("VO");
        Sheet sheetInsulation = wb.getSheet("Insulation");
        Sheet sheetF0 = wb.getSheet("F0");
        Sheet sheetMS = wb.getSheet("MS");

        TitulReport.generateVO(wb, report);
        ContentReport.generateVO(wb,report);

        // Определяем необходимость тех или иных протоколов
        setNecessaryProtocols();

        // Удаляем ненужные протоколы
        if (isVizual) wb = VOReport.generateVO(wb, report);
        else wb.removeSheetAt(wb.getSheetIndex(sheetVO));

        if (isF0) wb = F0Report.generateF0(wb, report);
        else wb.removeSheetAt(wb.getSheetIndex(sheetF0));

        if (isInsulation) wb = InsulationReport.generateInsulation(wb, report);
        else wb.removeSheetAt(wb.getSheetIndex(sheetInsulation));

        if (isMetallicBond) wb = MSReport.generateMS(wb, report);
        else wb.removeSheetAt(wb.getSheetIndex(sheetMS));

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

    public static void fillMainData(Sheet sheet, int column, ReportEntity report, ExcelStyle excelStyle){
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

    public static void fillWeather(Sheet sheet,int row, ReportEntity report, ExcelStyle excelStyle){
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
