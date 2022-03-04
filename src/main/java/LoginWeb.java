import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class LoginWeb {

    private List<String> cookies;
    private HttpURLConnection conn;
    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        String url = "http://hhmss:8080/iConfigurator/iconfigurator/wicket/page?1-1.IFormSubmitListener-contentPanel-signInPanel-signInForm";
        LoginWeb http = new LoginWeb();

        CookieHandler.setDefault(new CookieManager());

        //Send GET request, for extracting data
        String page = http.getPageContent(url);
        //getFormParams funktioniert nicht, deshalb wird das hier "hart" mitgegeben
        //String postParams = http.getFormParams(page, "tsadmin", "tsadmin");
        String postParams = "signInForm6_hf_0=&username=tsadmin&password=tsadmin&btnLogin=Anmelden";

        http.sendPost(url, postParams);

        String result = http.getPageContent(url);
        System.out.println(result);
    }

    private void sendPost(String url, String postParams) throws IOException {
        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();

        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", "hhmss:8080");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        conn.setRequestProperty("Accept-Language", "de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7");
        /*
        for(String cookie : this.cookies){
            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
        }

         */
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", "http://hhmss:8080/iConfigurator/iconfigurator/");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        conn.setDoOutput(true);
        conn.setDoInput(true);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
    }

    private String getPageContent(String url) throws IOException {
        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);

        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        conn.setRequestProperty("Accept-Language", "de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7");
        /*
        for(String cookie : this.cookies){
            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
        }

         */

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();

        setCookies(conn.getHeaderFields().get("Set-Cookie"));
        return response.toString();
    }

    public String getFormParams(String html, String username, String password) throws UnsupportedEncodingException {
        System.out.println("Extracting form's data...");
        Document doc = Jsoup.parse(html);

        Element loginForm = doc.getElementById("signInForm6_hf_0");
        Elements inputElements = loginForm.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();

        for(Element inputElement : inputElements){
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");


            if(key.equals("username")){
                value = username;
            } else if(key.equals("password")){
                value = password;
            }


            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));

        }

        StringBuilder result = new StringBuilder();
        for(String param : paramList){
            if(result.length() == 0){
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return result.toString();
    }

    public List<String> getCookies(){
        return cookies;
    }

    public void setCookies(List <String> cookies){
        this.cookies = cookies;
    }

}
