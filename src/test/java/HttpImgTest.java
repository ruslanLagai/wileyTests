import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpImgTest {

    private static String ADDRESS = "https://httpbin.org/image/png";
    private static URL url;
    private static String PATH = "src/main/resources/file.png";
    private static HttpURLConnection conn;
    private static int responceCode;

    @Before
    public void before() throws IOException {

        url = new URL(ADDRESS);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.connect();
    }

    @After
    public void after() {
        conn.disconnect();
    }

    /*
       Test to check the response on https://httpbin.org/image/png. Received image is saving in Resources folder
     */
    @Test
    public void responseStatusTest() throws IOException {

        responceCode = conn.getResponseCode();
        Assert.assertEquals(responceCode, HttpURLConnection.HTTP_OK);

        InputStream inputStream = conn.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(new File (PATH));
        int bytesRead;
        while ((bytesRead = inputStream.read()) != -1) {
            outputStream.write(bytesRead);
        }
        inputStream.close();
        outputStream.close();

     }

}
