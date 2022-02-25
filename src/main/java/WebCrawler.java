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
        Elements licenseType = document.select("tr");
        String lType = licenseType.text();

        int counter = 0;
        int intUsedModeling = 0;
        int modelingInt1 = 0;
        int modelingInt2 = 0;
        int modelingInt3 = 0;

        HashMap<String, String> licenceMap = new HashMap<String, String>();

        for(Element row : document.select("table tr")){

            //Der #23 steht für die Seite mit allen zusammengefassten Modeling-Lizenzen
            if(lType.contains("#23")){

                if(row.select("td:nth-of-type(1)").text().equals("Modeling")){

                    if(row.select("td:nth-of-type(1)").text().equals("")){
                        continue;
                    } else {
                        String safeUsed = output("3", row);
                        intUsedModeling = Integer.parseInt(safeUsed) / 2;
                    }
                } else if(row.select("td:nth-of-type(1)").text().equals("Model Manager")){

                    if(row.select("td:nth-of-type(1)").text().equals("")){
                        continue;
                    } else {
                        String safeUsed = output("3", row);
                        int intUsed = Integer.parseInt(safeUsed) / 2;
                        safeUsed = Integer.toString(intUsed);
                        licenceMap.put("Model Manager", safeUsed);
                    }
                }
            } else {
                String safeUsedModeling;
                int safeUsedInt;

                if(row.select("td:nth-of-type(1)").text().equals("Modeling")){

                    if(row.select("td:nth-of-type(1)").text().equals("")) {
                        continue;
                    } else {

                        safeUsedModeling = output("4", row);
                        safeUsedInt = Integer.parseInt(safeUsedModeling) / 2;

                        //Hier müssen die drei unterschiedlichen Modeling-Lizenzen einzeln gespeichert werden
                        if(counter == 0){
                            modelingInt1 = safeUsedInt;
                        } else if(counter == 1){
                            modelingInt2 = safeUsedInt;
                        } else if(counter == 2){
                            modelingInt3 = safeUsedInt;
                        }

                        counter ++;
                    }

                } else if(row.select("td:nth-of-type(1)").text().equals("Model Manager")){

                    if(row.select("td:nth-of-type(1)").text().equals("")){
                        continue;
                    } else {
                        String safeUsed = output("4", row);
                        int intUsed = Integer.parseInt(safeUsed) / 2;
                        safeUsed = Integer.toString(intUsed);
                        licenceMap.put("Model Manager", safeUsed);
                    }
                }
            }
        }
        //Letzte Abfrage ist nötig, weil sonst die falsche LicenceMap returned wird
        if(lType.contains("#23")){
            String finalUsedModeling = Integer.toString(intUsedModeling);
            licenceMap.put("Modeling", finalUsedModeling);
            return licenceMap;
        } else {
            intUsedModeling = modelingInt1 + modelingInt2 + modelingInt3;
            String finalUsedModeling = Integer.toString(intUsedModeling);
            licenceMap.put("Modeling", finalUsedModeling);
            return licenceMap;
        }

    }

    private static String output(String columnNumber, Element row) {
        final String line = row.select("td:nth-of-type("+columnNumber+")").text();
        String out = line;
        return out;
    }

}
