package com.task.ExcelReader.service;


import com.task.ExcelReader.dao.ExcelRepo;
import com.task.ExcelReader.model.Excel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

@Service
public class ExcelService {
    @Autowired
    ExcelRepo excelRepo;
    public ResponseEntity<?> uploadExcelDataToDB(MultipartFile readExcelDataFile) {
        List<Excel> excelList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(readExcelDataFile.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);
            for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                Excel tempExcel = new Excel();

                XSSFRow row = worksheet.getRow(i);
                if (row == null || isRowEmpty(row)) {
                    continue; // Skip empty rows
                }

                tempExcel.setMawb(row.getCell(0).getStringCellValue());
                tempExcel.setManifestDate(row.getCell(1).getDateCellValue());
                tempExcel.setAccountNumber(row.getCell(2).getStringCellValue());
                tempExcel.setAwb(row.getCell(3).getRawValue());
                tempExcel.setOrderNumber((row.getCell(4).getStringCellValue()));
                tempExcel.setOrigin(row.getCell(5).getStringCellValue());
                tempExcel.setDestination(row.getCell(6).getStringCellValue());
                tempExcel.setShipperName(row.getCell(7).getStringCellValue());
                tempExcel.setConsigneeName(row.getCell(8).getStringCellValue());
                tempExcel.setWeight(row.getCell(9).getNumericCellValue());
                tempExcel.setDeclaredValue(row.getCell(10).getNumericCellValue());
                tempExcel.setValue(row.getCell(11).getNumericCellValue());
                tempExcel.setVatAmount(row.getCell(12).getNumericCellValue());
                tempExcel.setCustomForm(row.getCell(13).getNumericCellValue());
                tempExcel.setOther(row.getCell(14).getNumericCellValue());
                tempExcel.setTotalCharges(row.getCell(15).getNumericCellValue());
                tempExcel.setCustomDeclaration(row.getCell(16).getNumericCellValue());
                tempExcel.setRef(row.getCell(17).getNumericCellValue());
                tempExcel.setCustomDeclarationDate(row.getCell(18).getDateCellValue());
                excelRepo.save(tempExcel);
                excelList.add(tempExcel);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(excelList, HttpStatus.OK);
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
