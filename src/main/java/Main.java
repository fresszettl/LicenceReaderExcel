import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        WebCrawler webCrawler = new WebCrawler();
        ExcelUpdater excelUpdater = new ExcelUpdater();
        excelUpdater.updateExcelFile(webCrawler.readWeb());

        DaySheet daySheet = new DaySheet();
        daySheet.writeDaySheet(webCrawler.readWeb());
    }

}
