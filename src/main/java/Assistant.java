import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;


public class Assistant {
    private static HttpURLConnection connection;

    public static void main(String[] args) {
        System.out.println("Sending request...");
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
//        String jsonResponse;


        try {
            String link = "http://192.168.10.98";
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            System.out.println(responseContent.toString());
            String jsonString = responseContent.toString();

            JSONObject obj = new JSONObject(jsonString);
            JSONObject meters_data = obj.getJSONObject("readings");
            JSONObject hum = meters_data.getJSONObject("humidity");
            System.out.println(hum);

        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}

