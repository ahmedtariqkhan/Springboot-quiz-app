package com.task.ExcelReader.controller;

import com.task.ExcelReader.model.Excel;
import com.task.ExcelReader.service.ExcelHelper;
import com.task.ExcelReader.service.ExcelService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("upload")
public class ExcelController {

    @Autowired
    ExcelService excelService;
    @PostMapping("excel")
    public ResponseEntity<?> uploadExcelDataToDB(@RequestParam("file") MultipartFile readExcelDataFile) throws IOException {
        return excelService.uploadExcelDataToDB(readExcelDataFile);
//        return ExcelHelper.parseExcelFile(readExcelDataFile);
    }
}
