package com.shuinvy.game_platform.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExcelService {

    public Workbook export(
            List<String>labels,
            List<List<String>> dataList) throws SQLException {
        // Use SXSSFWorkbook  if we will output huge data,
        // for example, 10,000 rows
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet 1");
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        int numOfColumns = labels.size();
        // Address header cells
        for (int n = 0; n < numOfColumns; n++) {
            String label = labels.get(n);
            Cell cell = row.createCell(n);
            cell.setCellValue(label);
        }
        for (List<String> data : dataList) {
            row = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int  n = 0; n < data.size(); n++) {
                Cell cell = row.createCell(n);
                cell.setCellValue(data.get(n));
            }
        }
        return workbook;
    }
}
