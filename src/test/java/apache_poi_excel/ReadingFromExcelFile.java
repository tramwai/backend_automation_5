package apache_poi_excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;


public class ReadingFromExcelFile {

    static Logger logger = LogManager.getLogger(ReadingFromExcelFile.class.getClasses());

    public static void main(String[] args) throws IOException {

        // assigning the file path
        String excelFilePath = "test_data/ReadData.xlsx";

        // reaching for the file
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);

        // opening the file from the specified path
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

        // Go into the specific sheet in the workbook
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        // getting the first name of the first name column from the file
        String firstName = sheet.getRow(1).getCell(0).getStringCellValue();
        logger.info("First name from the first cell is " + firstName);

        // getting the second Id from the file column
        double secondId = sheet.getRow(2).getCell(1).getNumericCellValue();
        logger.info("Second Id value is " + secondId);

        // getting the last row number from the file
        // rows are by index
        int lastRow = sheet.getLastRowNum();
        logger.info("the last row number is " + lastRow);

        // getting the last cell number
        // cells are by number of the cells
        int lastCell = sheet.getRow(1).getLastCellNum();
        logger.info("The last cell number from the file is " + lastCell);

        // Looping each data(getting all the data)
        for(int r = 0; r <= lastRow; r++){
            // visiting each row
            XSSFRow row = sheet.getRow(r);

            // looping each cell with corresponding row
            for (int c = 0; c < lastCell; c++) {
                // getting each cell value from that row
                XSSFCell cell = row.getCell(c);
                System.out.print(cell + " | ");
            }
            System.out.println();
        }




    }
}
