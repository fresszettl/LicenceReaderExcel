import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class ExcelUpdater {

    public static String excelFilePath = "\\\\hhinstall2\\EDV\\IT-Hardware\\CAD\\MEls\\statistik.xls";

    public void updateExcelFile(HashMap<String, String> webCrawler) throws IOException {


        //String excelFilePath = "C:\\Users\\BecFab01\\Desktop\\statistik.xls";

        FileInputStream fileIS = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(fileIS);
        Sheet sheet = workbook.getSheetAt(0);

        HashMap<String, String> licenceMap = webCrawler;

        Row lastRow = sheet.getRow(sheet.getLastRowNum());

        if(lastRow.getCell(0).getStringCellValue().equals(getDate())){
            boolean flagModeling = CompareWorkload.compareWorkloades("Modeling", 2);
            boolean flagMM = CompareWorkload.compareWorkloades("Model Manager", 7);

            if(flagModeling == true && flagMM == true) {
                lastRow.createCell(0).setCellValue(getDate());
                lastRow.createCell(1).setCellValue(getTime());
                lastRow.createCell(2).setCellValue(licenceMap.get("Modeling"));
                lastRow.createCell(7).setCellValue(licenceMap.get("Model Manager"));

            } else if(flagModeling == true && flagMM == false) {
                lastRow.createCell(0).setCellValue(getDate());
                lastRow.createCell(1).setCellValue(getTime());
                lastRow.createCell(2).setCellValue(licenceMap.get("Modeling"));

            } else if(flagModeling == false && flagMM == true) {
                lastRow.createCell(0).setCellValue(getDate());
                lastRow.createCell(1).setCellValue(getTime());
                lastRow.createCell(7).setCellValue(licenceMap.get("Model Manager"));

            }
        } else {
            Row dataRow = sheet.createRow(sheet.getLastRowNum()+1);
            dataRow.createCell(0).setCellValue(getDate());
            dataRow.createCell(1).setCellValue(getTime());
            dataRow.createCell(2).setCellValue(licenceMap.get("Modeling"));
            dataRow.createCell(7).setCellValue(licenceMap.get("Model Manager"));
        }

        fileIS.close();

        FileOutputStream fileOS = new FileOutputStream(excelFilePath);
        workbook.write(fileOS);
        fileOS.close();
        System.out.println("Main Excel updated successfully!");

    }

    public static String getDate(){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateNow = date.format(LocalDateTime.now());
        return dateNow;
    }

    public static String getTime(){
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
        String timeNow = time.format(LocalDateTime.now());
        return timeNow;
    }

}
