import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class CompareWorkload {

    public static double getCurrentWorkload(String license) throws IOException {
        HashMap<String, String> licenseMap = WebCrawler.readWeb();

        String value = licenseMap.get(license);
        double valueDouble = Double.parseDouble(value);
        return valueDouble;
    }

    public static double getLastHighestWorkload(int cellNum) throws EncryptedDocumentException, IOException {
        String excelFilePath = "C:\\Users\\BecFab01\\Desktop\\statistik.xls";
        FileInputStream fileIS = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(fileIS);
        Sheet sheet = workbook.getSheetAt(0);
        Row dataRow = sheet.getRow(sheet.getLastRowNum());
        String modelingValue = dataRow.getCell(cellNum).toString();
        double lastHighestModelingDouble = Double.parseDouble(modelingValue);
        return lastHighestModelingDouble;
    }

    public static boolean compareWorkloades(String license, int cellNum) throws IOException {
        double compareV1 = getCurrentWorkload(license);
        double compareV2 = getLastHighestWorkload(cellNum);
        if(compareV1 > compareV2) {
            return true;
        } else {
            return false;
        }
    }

}
