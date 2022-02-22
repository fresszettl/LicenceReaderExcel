import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class DaySheet {

    public static void createDaySheet(Workbook workbook){
        Sheet sheet = workbook.createSheet(ExcelUpdater.getDate());

        Row headLineRow = sheet.createRow(0);
        Cell modeling = headLineRow.createCell(1);
        Cell mm = headLineRow.createCell(2);
        Cell time = headLineRow.createCell(0);
        modeling.setCellValue("Modeling");
        mm.setCellValue("Model Manager");
        time.setCellValue("Time");
        workbook.setActiveSheet(workbook.getActiveSheetIndex()+1); //Active sheet has to be the current date
        System.out.println("New Day Sheet created!");
    }

    public void writeDaySheet(HashMap<String, String> webCrawler) throws IOException {
        String excelFilePath = "C:\\Users\\BecFab01\\Desktop\\statistik.xls";
        FileInputStream fileIS = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(fileIS);

        if(!hasWorkbookSheetWithName(workbook)) {

            DaySheet.createDaySheet(workbook);

        }

        Sheet sheet = workbook.getSheet(ExcelUpdater.getDate());
        HashMap<String, String> licenceMap = webCrawler;
        Row mainRow = sheet.createRow(sheet.getLastRowNum()+1);
        mainRow.createCell(0).setCellValue(ExcelUpdater.getTime());
        mainRow.createCell(1).setCellValue(licenceMap.get("Modeling"));
        mainRow.createCell(2).setCellValue(licenceMap.get("Model Manager"));

        fileIS.close();

        FileOutputStream fileOS = new FileOutputStream(excelFilePath);
        workbook.write(fileOS);
        fileOS.close();
        System.out.println("Day Sheet updated successfully!");
    }

    public static boolean hasWorkbookSheetWithName(Workbook workbook){
        for(int i=0; i<workbook.getNumberOfSheets(); i++){
            String sheetName = workbook.getSheetName(i);
            if(sheetName.equals(ExcelUpdater.getDate())){
                return true;
            }
        }
        return false;
    }

}
