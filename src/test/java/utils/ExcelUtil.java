package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    static Logger logger = LogManager.getLogger(ExcelUtil.class);
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static String filePath;

    public static void openExcelFile(String fileName, String sheetName){
        filePath = "test_data/" + fileName + ".xlsx";

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            logger.info("File " + fileName + " exists");
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);
            logger.info("Sheet " + sheetName + " exists");
        }catch (Exception e){
            logger.debug(fileName + " and " + sheetName + " cannot be found");
        }
    }

    // getting the single value from the Excel file
    public static String getValue(int rowNumber, int cellNUmber){
        row = sheet.getRow(rowNumber);
        cell = row.getCell(cellNUmber);
        return cell.toString();
    }

    // getting al the values
    public static List<List<String>> getValues(){
        // creating List of List to store all the values from the Excel file
        List<List<String>> allValues = new ArrayList<>();

        // creating loops for getting the rows
        for (int r = sheet.getFirstRowNum() + 1; r <= sheet.getLastRowNum(); r++) {
            // creating a row in the sheet
            row = sheet.getRow(r);
            // creating a list for storing row data
            List<String> eachRow = new ArrayList<>();
            // looping each cell in the corresponding row
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                /// get each cell value and add them into eachRow list
                eachRow.add(getValue(r,c));
            }
            // adding each list of row to the list of list
            allValues.add(eachRow);
        }
        // returning the list of list
        return allValues;
    }

    // convert
    public static String[][] getExcelData(List<List<String>> listOfList){
        // Creation of multidimensional array
        String[][] result = new String[listOfList.size()][];

        // loop the lists to fetch the data and put it into an array
        for (int i = 0; i < listOfList.size(); i++) {
            // getting the values from each list
            List<String> currentList = listOfList.get(i);

            // converting the List into Array and add to the multidimensional array
            result[i] = currentList.toArray(new String[currentList.size()]);
        }
        return result;
    }
    // close the workbook
    public static void closingExcelFile(){
        try {
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
