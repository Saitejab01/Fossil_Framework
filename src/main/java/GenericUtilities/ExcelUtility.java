package GenericUtilities;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class ExcelUtility {


    private Workbook workbook;
    private String filePath;


    // Constructor
    public ExcelUtility(String filePath) {


        this.filePath = filePath;


        try (FileInputStream fis = new FileInputStream(filePath)) {


           workbook=WorkbookFactory.create(fis);


        } catch (IOException e) {
            throw new RuntimeException("Unable to read Excel file", e);
        }
    }


    // Get Sheet
    public Sheet getSheet(String sheetName) {


        Sheet sheet = workbook.getSheet(sheetName);


        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }


        return sheet;
    }


    // Get Row Count
    public int getRowCount(String sheetName) {


        Sheet sheet = getSheet(sheetName);


        return sheet.getLastRowNum() + 1;
    }


    // Get Cell Count
    public int getCellCount(String sheetName, int rowNum) {


        Row row = getSheet(sheetName).getRow(rowNum);


        return row == null ? 0 : row.getLastCellNum();
    }


    // Get Cell Data
    public String getCellData(String sheetName, int rowNum, int cellNum) {


        Row row = getSheet(sheetName).getRow(rowNum);


        if (row == null) {
            return "";
        }


        Cell cell = row.getCell(cellNum);


        if (cell == null) {
            return "";
        }


        DataFormatter formatter = new DataFormatter();


        return formatter.formatCellValue(cell);
    }



 // Fetch Test Data Based on Unique Test Case ID
 public Map<String, String> getTestData(String sheetName, String uniqueData) {

     Map<String, String> testData = new LinkedHashMap<>();

     Sheet sheet = getSheet(sheetName);

     DataFormatter formatter = new DataFormatter();

     // Search for the Test Case ID
     for (int i = 0; i <= sheet.getLastRowNum(); i++) {

         Row dataRow = sheet.getRow(i);

         if (dataRow == null) {
             continue;
         }

         String testCaseID =
                 formatter.formatCellValue(dataRow.getCell(0)).trim();

         // Check whether the current row contains the requested TC_ID
         if (testCaseID.equalsIgnoreCase(uniqueData.trim())) {

             // Find the corresponding header row
             // It must be immediately above the data row
             if (i == 0) {
                 throw new RuntimeException(
                         "Header row not found for: " + uniqueData);
             }

             Row headerRow = sheet.getRow(i - 1);

             if (headerRow == null) {
                 throw new RuntimeException(
                         "Header row is empty for: " + uniqueData);
             }

             // Fetch header and corresponding cell values
             for (int j = 0; j < headerRow.getLastCellNum(); j++) {

                 String header =
                         formatter.formatCellValue(headerRow.getCell(j)).trim();

                 if (header.isBlank()) {
                     continue;
                 }

                 String value =
                         formatter.formatCellValue(dataRow.getCell(j)).trim();

                 testData.put(header, value);
             }

             System.out.println("Test Case ID: " + uniqueData);
             System.out.println("Retrieved Test Data: " + testData);

             return testData;
         }
     }

     throw new RuntimeException(
             "Test Case ID not found: " + uniqueData);
 }

    // Write Cell Data
    public void setCellData(String sheetName, int rowNum, int cellNum, String data) {


        Sheet sheet = getSheet(sheetName);


        Row row = sheet.getRow(rowNum);


        if (row == null) {
            row = sheet.createRow(rowNum);
        }


        Cell cell = row.getCell(cellNum);


        if (cell == null) {
            cell = row.createCell(cellNum);
        }


        cell.setCellValue(data);
    }


    // Save Excel
    public void saveExcel() {


        try (FileOutputStream fos = new FileOutputStream(filePath)) {


            workbook.write(fos);


        } catch (IOException e) {
            throw new RuntimeException("Unable to save Excel file", e);
        }
    }


    // Close Excel
    public void closeExcel() {


        try {


            if (workbook != null) {
                workbook.close();
            }


        } catch (IOException e) {
            throw new RuntimeException("Unable to close Excel workbook", e);
        }
    }


    public void writeBackData(String sheetName, String uniqueData,
            String header, String writeBackData) {


        Sheet sheet = getSheet(sheetName);


        Row headerRow = sheet.getRow(0);


        DataFormatter formatter = new DataFormatter();


        int headerColumn = -1;
        int dataRow = -1;


        // Identify Header Column
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {


            String headerName = formatter.formatCellValue(headerRow.getCell(i));


            if (headerName.equalsIgnoreCase(header)) {
                headerColumn = i;
                break;
            }
        }


        if (headerColumn == -1) {
            throw new RuntimeException("Header not found: " + header);
        }


        // Identify Unique Test Case ID Row
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {


            Row row = sheet.getRow(i);


            if (row == null) {
                continue;
            }


            String testCaseID = formatter.formatCellValue(row.getCell(0));


            if (testCaseID.equalsIgnoreCase(uniqueData)) {
                dataRow = i;
                break;
            }
        }


        if (dataRow == -1) {
            throw new RuntimeException("Test Case ID not found: " + uniqueData);
        }


        // Write Data
        Row row = sheet.getRow(dataRow);


        Cell cell = row.getCell(headerColumn);


        if (cell == null) {
            cell = row.createCell(headerColumn);
        }


        cell.setCellValue(writeBackData);


        // Save Excel
        saveExcel();
    }
}