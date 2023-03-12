package apache_poi_excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class WritingDataToExcelFile {
    public static void main(String[] args) throws IOException {

        // getting the filepath
        String filePath = "test_data/WriteData.xlsx";

        // created a workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create the specific sheet in the workbook
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        // created a row
        XSSFRow row = sheet.createRow(0);

        // created a cell
        XSSFCell cell = row.createCell(0);

        // set the cell value
        cell.setCellValue("Tech Global");

        // Store the filepath into the system
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);

        // complete writing process on the file
        workbook.write(fileOutputStream);

        // close the file after writing
        fileOutputStream.close();
    }
}
