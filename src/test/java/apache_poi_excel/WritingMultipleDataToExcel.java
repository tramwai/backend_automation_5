package apache_poi_excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WritingMultipleDataToExcel {
    public static void main(String[] args) throws IOException {

        String filePath = "test_data/WriteData.xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Sheet2");

        Object[][] employeeData = {
                // column name
                {"EmpID", "Name", "Title"},
                {101, "Tech Global", "DevOps"},
                {102, "Ulan", "Developer"},
                {103, "Abe", "Instructor"},
        };

        // getting the length of the multidimensional array
        // it will be our reference for the number of rows we will put in the Excel file
        int rowLength = employeeData.length;
        System.out.println("Length of the multidimensional array is " + rowLength);

        // get cell length we can put in the row
        int cellLength = employeeData[0].length;
        System.out.println("Length of the single array in the multidimensional array is " + cellLength);

        // Creating the rows in the excel file
        for (int r = 0; r < rowLength; r++) {
            // getting the corresponding row
            XSSFRow row = sheet.createRow(r);

            // inner loop creating the cells for each row
            for (int c = 0; c < cellLength ; c++) {
                // creating the cell for that row
                XSSFCell cell = row.createCell(c);

                // getting the corresponding data from the array based on the indexes of nested loop
                Object cellValue = employeeData[r][c];
                // check each data from the array and write it into the excel file
                if (cellValue instanceof String)
                    cell.setCellValue((String) cellValue);
                if (cellValue instanceof Integer)
                    cell.setCellValue((Integer) cellValue);
                if (cellValue instanceof Boolean)
                    cell.setCellValue((Boolean) cellValue);
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        workbook.write(fileOutputStream);
        workbook.close();

    }
}
