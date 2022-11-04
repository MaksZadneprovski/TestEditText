package com.example.testedittext.click_handlers.test;

import android.content.Context;

import com.example.testedittext.R;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MeasureInsulationSaver {

    private  String fileName;
    private Context context;

    public MeasureInsulationSaver(String fileName, Context context) {
        this.fileName = fileName;
        this.context = context;
    }

    public void save() throws IOException {

        //Workbook wb2 = WorkbookFactory.create(new FileInputStream("w.xls"));
        //Workbook wb2 = WorkbookFactory.create(context.getResources().getAssets().open("raw/aaaa.xls"));
        Workbook wb2 = WorkbookFactory.create(context.getResources().openRawResource(R.raw.insulation));
        Sheet sheet1 = wb2.getSheet("list1");
        Row row = sheet1.getRow(2);
        Cell cell = row.getCell(0);
        Cell cell2 = row.getCell(1);
        cell.setCellValue("qwerty");

        // Создаем стиль для создания рамки у ячейки
        CellStyle style = wb2.getCellStyleAt(0);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cell2.setCellStyle(style);
        cell.setCellStyle(style);

        cell2.setCellValue("383");




        //try (OutputStream fileOut = new FileOutputStream("w.xls")) {
        //try (OutputStream fileOut = context.getAssets().("workbook2.xls")) {
        try(FileOutputStream fileOut = new FileOutputStream(getExternalPath())) {
            wb2.write(fileOut);
        }
        wb2.close();
    }


    private File getExternalPath() {
        return new File(context.getExternalFilesDir(null), fileName);
    }
}
