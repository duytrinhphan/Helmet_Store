package com.example.demo.util;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.text.Format;
import java.text.SimpleDateFormat;

public class ExcelUtils {

    public static boolean checkNullCell(Cell obj) {
        if (DataUltil.isNullObject(obj)) {
            return true;
        }

        Object cellValue = null;
        switch (obj.getCellTypeEnum()) {
            case STRING:
                cellValue = obj.getStringCellValue();
                String objStr = DataUltil.safeToString(cellValue);
                objStr = objStr.replaceAll("&nbsp;", "");
                objStr = objStr.replaceAll("\\s{2,}+", "");
                cellValue = objStr.trim();
                break;
            case NUMERIC:
                cellValue = obj.getNumericCellValue();
                break;
            case FORMULA:
                cellValue = obj.getRichStringCellValue();
                break;
            default:
                break;
        }

        if (DataUltil.isNullObject(cellValue) || "Null".equalsIgnoreCase(DataUltil.safeToString(cellValue))) {
            return true;
        }
        return false;
    }

    public static boolean checkNullLCells(Row row, int num) {
        boolean check = true;

        for (int i = 0; i < num; i++) {
            if (!checkNullCell(row.getCell(i))) {
                check = false;
            }
        }

        return check;
    }

    public static Object getValueOfCell(Cell cell) {
        if (DataUltil.isNullObject(cell)) {
            return null;
        }
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                double dataCell = cell.getNumericCellValue();
                return (new Double(dataCell)).longValue();
            case STRING:
                return cell.getStringCellValue();
            case FORMULA:
                return cell.getRichStringCellValue();
            default:
                return null;
        }
    }

    public static String getCellString(Cell cell) {
        if (DataUltil.isNullObject(cell)) {
            return null;
        }

        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                double dataCell = cell.getNumericCellValue();
                return DataUltil.safeToString(new Double(dataCell).intValue());
            case STRING:
                return cell.getStringCellValue();
            default:
                return null;
        }
    }

    public static Double getCellDouble(Cell cell) {
        if (DataUltil.isNullObject(cell)) {
            return null;
        }

        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                return DataUltil.parseToDouble(cell.getStringCellValue());
            default:
                return null;
        }
    }

    public static Long getCellLong(Cell cell) {
        if (DataUltil.isNullObject(cell)) {
            return null;
        }

        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                double dataCell = cell.getNumericCellValue();
                return (new Double(dataCell)).longValue();
            case STRING:
                return DataUltil.parseToLong(cell.getStringCellValue());
            default:
                return null;
        }
    }

    public static boolean isNumeric(Cell cell) {
        if (cell == null) {
            return false;
        }
        try {
            Double.parseDouble(getCellString(cell));
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String getCellDateString(Cell cell) {
        if (DataUltil.isNullObject(cell)) {
            return null;
        }

        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                Format format = new SimpleDateFormat("dd/MM/yyyy");
                return format.format(cell.getDateCellValue()).trim();
            case STRING:
                return cell.getStringCellValue().trim();
            default:
                return null;
        }
    }

    public static Long getCellDatetime(Cell cell) throws Exception {
        if (DataUltil.isNullObject(cell)) {
            return null;
        }

        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                return cell.getDateCellValue().getTime();
            case STRING:
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                return formatter.parse(cell.getStringCellValue()).getTime();
            default:
                return null;
        }
    }

}
