import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class WebCrawler {

    public static HashMap<String, String> readWeb() throws IOException {
        String url = "http://hhcadls:17171/s.html";

        Document document = Jsoup.connect(url).get();
        Elements button = document.getElementsByAttributeValueContaining("name", "show_upgrades");
        int counter = 0;

        HashMap<String, String> licenceMap = new HashMap<String, String>();

        for(Element row : document.select("table tr")){

            if(button.isEmpty()){

                if(row.select("td:nth-of-type(1)").text().equals("Modeling")){

                    if(row.select("td:nth-of-type(1)").text().equals("")){
                        continue;
                    } else {
                        String safeUsed = output("3", row, "Currently used");
                        int intUsed = Integer.parseInt(safeUsed) / 2;
                        safeUsed = Integer.toString(intUsed);
                        licenceMap.put("Modeling", safeUsed);
                    }
                } else if(row.select("td:nth-of-type(1)").text().equals("ModelManager")){

                    if(row.select("td:nth-of-type(1)").text().equals("")){
                        continue;
                    } else {
                        String safeUsed = output("3", row, "Currently used");
                        int intUsed = Integer.parseInt(safeUsed) / 2;
                        safeUsed = Integer.toString(intUsed);
                        licenceMap.put("Model Manager", safeUsed);
                    }
                }
            } else {

                if(row.select("td:nth-of-type(1)").text().equals("Modeling")){
                    if(row.select("td:nth-of-type(1)").text().equals("")) {
                        continue;
                    } else {

                        String line = row.select("td:nth-of-type(4)").text();
                        counter = counter + Integer.parseInt(line);
                        counter = counter / 2;
                        String safeUsed = String.valueOf(counter);
                        licenceMap.put("Modeling", safeUsed);

                    }
                } else if(row.select("td:nth-of-type(1)").text().equals("Model Manager")){

                    if(row.select("td:nth-of-type(1)").text().equals("")){
                        continue;
                    } else {
                        String safeUsed = output("4", row, "Currently used");
                        int intUsed = Integer.parseInt(safeUsed) / 2;
                        safeUsed = Integer.toString(intUsed);
                        licenceMap.put("Model Manager", safeUsed);
                    }
                }
            }
        }
        return licenceMap;
    }

    private static String output(String columnNumber, Element row, String rowText) {
        final String line = row.select("td:nth-of-type("+columnNumber+")").text();
        String out = line;
        return out;
    }

}
