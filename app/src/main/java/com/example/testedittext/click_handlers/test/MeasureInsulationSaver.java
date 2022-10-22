//package com.example.testedittext.click_handlers.test;
//
//import android.content.Context;
//import android.view.View;
//
//import androidx.constraintlayout.widget.ConstraintLayout;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class MeasureInsulationSaver implements View.OnClickListener {
//
//    private  String fileName;
//    private List<ConstraintLayout> cLList;
//    private Context context;
//    private InsulationActivity activity;
//
//    public MeasureInsulationSaver(List<ConstraintLayout> cLList, InsulationActivity activity) {
//        this.cLList = cLList;
//        this.activity = activity;
//    }
//
//    public void save() throws IOException {
//
//        //Workbook wb2 = WorkbookFactory.create(new FileInputStream("w.xls"));
//        Workbook wb2 = WorkbookFactory.create(context.getResources().getAssets().open("w.xls"));
//        Sheet sheet1 = wb2.getSheet("list1");
//        Row row = sheet1.getRow(2);
//        Cell cell = row.getCell(0);
//        System.out.println(cell.getStringCellValue());
//        cell.setCellValue("nahui suka");
//
//
//
//
//        //try (OutputStream fileOut = new FileOutputStream("w.xls")) {
//        //try (OutputStream fileOut = context.getAssets().("workbook2.xls")) {
//        //}
//        try(FileOutputStream fileOut = new FileOutputStream(getExternalPath())) {
//            wb2.write(fileOut);
//        }
//        wb2.close();
//    }
//    @Override
//    public void onClick(View view)  {
//        this.context = view.getContext();
//        this.fileName = activity.getFileName() + ".xls";
//
//        try {
//            save();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private File getExternalPath() {
//        return new File(context.getExternalFilesDir(null), fileName);
//    }
//}
