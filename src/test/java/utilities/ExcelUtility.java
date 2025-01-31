package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtility {

    private final Workbook workbook;
    private Sheet sheet;

    /**
     * Constructor to load the Excel file
     *
     * @param filePath Path of the Excel file
     */
    public ExcelUtility(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fileInputStream);
    }

    /**
     * Set the sheet to be accessed by name
     *
     * @param sheetName Name of the sheet to be accessed
     */
    public void setSheet(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in the Excel file.");
        }
    }

    /**
     * Get the total number of rows in the current sheet
     *
     * @return Total row count
     */
    public int getRowCount() {
        if (sheet == null) {
            throw new IllegalStateException("Sheet is not set. Use setSheet() before accessing rows.");
        }
        return sheet.getPhysicalNumberOfRows();
    }

    /**
     * Get the total number of columns in a specific row
     *
     * @param rowIndex Index of the row (0-based)
     * @return Total column count
     */
    public int getColumnCount(int rowIndex) {
        if (sheet == null) {
            throw new IllegalStateException("Sheet is not set. Use setSheet() before accessing columns.");
        }
        Row row = sheet.getRow(rowIndex);
        return (row == null) ? 0 : row.getPhysicalNumberOfCells();
    }

    /**
     * Get data from a specific cell
     *
     * @param rowIndex    Row index (0-based)
     * @param columnIndex Column index (0-based)
     * @return Cell data as a String
     */
    public String getCellData(int rowIndex, int columnIndex) {
        if (sheet == null) {
            throw new IllegalStateException("Sheet is not set. Use setSheet() before accessing cell data.");
        }

        Row row = sheet.getRow(rowIndex);
        if (row == null) return "";
        Cell cell = row.getCell(columnIndex);
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "Unknown Cell Type";
        }
    }

    /**
     * Close the workbook
     */
    public void closeWorkbook() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}
