package com.example.testedittext.utils;

import android.content.Context;

import com.example.testedittext.R;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;


public class DefectsParser {


    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        // Проверяем тип ячейки и возвращаем соответствующее значение
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }



    public static String mapToString(Map<String, List<Map<String, String>>> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for (Map.Entry<String, List<Map<String, String>>> entry : map.entrySet()) {
            sb.append("  \"").append(entry.getKey()).append("\": [\n");

            List<Map<String, String>> list = entry.getValue();
            for (Map<String, String> innerMap : list) {
                sb.append("    {\n");
                for (Map.Entry<String, String> innerEntry : innerMap.entrySet()) {
                    sb.append("      \"").append(innerEntry.getKey()).append("\": \"")
                            .append(innerEntry.getValue()).append("\",\n");
                }
                sb.deleteCharAt(sb.length() - 2); // Удаляем лишнюю запятую после последней записи внутренней карты
                sb.append("    },\n");
            }
            sb.deleteCharAt(sb.length() - 2); // Удаляем лишнюю запятую после последней записи списка
            sb.append("  ],\n");
        }
        sb.deleteCharAt(sb.length() - 2); // Удаляем лишнюю запятую после последней записи внешней карты
        sb.append("}");

        return sb.toString();

    }





    public static Map<String, List<Map<String, String>>> parseDefectsFromFile(Context context) throws IOException {
        Workbook wb;
        Map<String, List<Map<String, String>>> map = new HashMap<>();
        wb = WorkbookFactory.create(context.getResources().openRawResource(R.raw.defects));
        Sheet sheet = wb.getSheet("List");

        Iterator<Row> rowIterator = sheet.iterator();
        // Пропускаем первую строку, так как она содержит заголовки столбцов
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            // Проверяем, есть ли значения в строке
            boolean hasValues = false;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType() != CellType.BLANK) {
                    hasValues = true;
                    break;
                }
            }

            // Если строка содержит значения, обрабатываем ее
            if (hasValues) {
                String col1Value = getCellValue(row.getCell(0));
                String col2Value = getCellValue(row.getCell(1));
                String col3Value = getCellValue(row.getCell(2));

                List<Map<String, String>> innerList = map.getOrDefault(col1Value, new ArrayList<>());
                Map<String, String> innerMap = new HashMap<>();
                innerMap.put(col2Value, col3Value);
                innerList.add(innerMap);
                map.put(col1Value, innerList);
            }
        }

        return map;
    }

    public static String getString3FromMap(Map<String, List<Map<String, String>>> inputMap, String string2) {
        for (List<Map<String, String>> innerList : inputMap.values()) {
            for (Map<String, String> innerMap : innerList) {
                if (innerMap.containsKey(string2)) {
                    return innerMap.get(string2);
                }
            }
        }
        return null; // Если значение не найдено, возвращаем null или можно выбрать другое значение по умолчанию
    }

}
