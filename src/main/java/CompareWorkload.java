import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class CompareWorkload {

    public static double getCurrentWorkload(String licence) throws IOException {
        HashMap<String, String> licenceMap = WebCrawler.readWeb();

        String value = licenceMap.get(licence);
        double valueDouble = Double.parseDouble(value);
        return valueDouble;
    }

    public static double getLastHighestWorkload(int cellNum) throws EncryptedDocumentException, IOException {
        String excelFilePath = "\\\\hhinstall2\\EDV\\IT-Hardware\\CAD\\MEls\\statistik.xls";
        FileInputStream fileIS = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(fileIS);
        Sheet sheet = workbook.getSheetAt(0);
        Row dataRow = sheet.getRow(sheet.getLastRowNum());
        String modelingValue = dataRow.getCell(cellNum).toString();
        double lastHighestModelingDouble = Double.parseDouble(modelingValue);
        return lastHighestModelingDouble;
    }

    public static boolean compareWorkloades(String licence, int cellNum) throws IOException {
        double compareV1 = getCurrentWorkload(licence);
        double compareV2 = getLastHighestWorkload(cellNum);
        if(compareV1 > compareV2) {
            return true;
        } else {
            return false;
        }
    }

}
