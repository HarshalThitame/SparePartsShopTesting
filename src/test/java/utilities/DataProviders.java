package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public Object[][] getExcelData() throws IOException {
        // Path to the Excel file
        String filePath = "./testData/logincred.xlsx";
        String sheetName = "Sheet1";

        // Initialize ExcelUtility
        ExcelUtility excelUtility = new ExcelUtility(filePath);
        excelUtility.setSheet(sheetName);

        // Get total rows and columns
        int rowCount = excelUtility.getRowCount();
        int columnCount = excelUtility.getColumnCount(0);

        // Create a two-dimensional Object array to store the data
        Object[][] data = new Object[rowCount - 1][columnCount]; // Exclude header row

        // Loop through the rows and columns to populate the data
        for (int i = 1; i < rowCount; i++) { // Start from 1 to skip the header row
            for (int j = 0; j < columnCount; j++) {
                data[i - 1][j] = excelUtility.getCellData(i, j); // Add data to array
            }
        }

        // Close the workbook
        excelUtility.closeWorkbook();

        return data;
    }
}
