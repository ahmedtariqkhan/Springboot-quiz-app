package com.task.ExcelReader.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.sql.Types.NULL;
import static org.apache.tomcat.util.bcel.classfile.ElementValue.PRIMITIVE_BOOLEAN;
import static java.sql.Types.NUMERIC;
import static org.apache.tomcat.util.bcel.classfile.ElementValue.STRING;

public class ExcelHelper {
    public static List<Object> parseExcelFile(MultipartFile file) {
        List<Object> rows = new ArrayList<>();
        boolean foundLastWrittenRow = false;

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to parse the first sheet

            int lastRowNum = sheet.getLastRowNum();

            int rowNumber = 0;
//            for (Row row : sheet) {
////                if (flag && rowNumber > 0) {
////                    break; // Stop parsing after the first row when flag is true
////                }
//
//                List<Object> rowData = new ArrayList<>();
//                for (Cell cell : row) {
//                    Object cellValue = getCellValueAsObject(cell, workbook);
//                    rowData.add(cellValue);
//                }
//
//                rows.add(rowData);
//
//                if (rowData.isEmpty() || allStringsEmpty(rowData)) {
//                    foundLastWrittenRow = true; // Detecting empty row or row with all empty cells as the last written row
//                }
//
//                rowNumber++;
//            }
            for (int rowNum = 0; rowNum <= lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null || isRowEmpty(row)) {
                    continue; // Skip empty rows
                }

                List<Object> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    Object cellValue = getCellValueAsObject(cell, workbook);
                    rowData.add(cellValue);
                }

                rows.add(rowData);
            }

            workbook.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        return rows;
    }
    private static Object getCellValueAsObject(Cell cell, Workbook workbook) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return cell.getNumericCellValue();
            case PRIMITIVE_BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return cell.toString();
        }
    }

    private static boolean allStringsEmpty(List<Object> list) {
        for (Object item : list) {
            if (item instanceof String && (item == null || ((String) item).isEmpty())) {
                return true;
            }
        }
        return false;
    }
    private static boolean isRowEmpty(Row row) {
        if (row == null) {
            return true; // If the row itself is null, consider it empty
        }

        int lastCellNum = row.getLastCellNum();
        for (int cellNum = row.getFirstCellNum(); cellNum < lastCellNum; cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != NULL && !cell.toString().trim().isEmpty()) {
                return false; // If any cell is not null, not blank, and not empty, the row is not empty
            }
        }
        return true; // All cells in the row are either null, blank, or empty
    }
}
