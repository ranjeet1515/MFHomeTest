package track;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * api/recursive/ask API
 */
public class AskServerService {

    String seed;

    public AskServerService(String seed) {
        this.seed = seed;
    }

    public Integer request(int n) {
        Integer result = -1;
        String apiurl = "http://challenge-server.code-check.io/api/recursive/ask?n=" + n + "&seed=" + seed;

        HttpURLConnection urlConn = null;
        InputStream in = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiurl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.connect();
            int status = urlConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                in = urlConn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                result = Integer.parseInt(Utill.toJsonMapFromResponse(response.toString()).get("result"));
            } else {
                in = urlConn.getErrorStream();
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder error = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    error.append(line);
                }
                System.out.println("HTTP Error:" + error);
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (urlConn != null) {
                    urlConn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
}