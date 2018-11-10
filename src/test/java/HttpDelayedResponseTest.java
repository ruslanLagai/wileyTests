import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class HttpDelayedResponseTest {
    private static String delay;
    private static String ADDRESS = "https://httpbin.org/delay/";
    private static URL url;
    private static HttpURLConnection conn;
    private static int responceCode;
    private static String readLine;
    private static StringBuffer response;
    private static String result = "\"args\": {},\n" +
            "  \"data\": \"\",\n" +
            "  \"files\": {},\n" +
            "  \"form\": {},\n" +
            "  \"headers\": {\n" +
            "    \"Accept\": \"application/json\",\n" +
            "    \"Connection\": \"close\",\n" +
            "    \"Host\": \"httpbin.org\"";

    @Before
    public void before() throws IOException {

        delay = "10";
        url = new URL(ADDRESS + delay);
        response = new StringBuffer();
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
    Test to check delayed response on https://httpbin.org/delay/
     */
    @Test
    public void responseStatusTest() throws IOException {

        Calendar t = Calendar.getInstance();
        responceCode = conn.getResponseCode();
        Assert.assertEquals(responceCode, HttpURLConnection.HTTP_OK);

        Calendar t1 = Calendar.getInstance();
        long period = Math.round(t1.getTimeInMillis() / 1000 - t.getTimeInMillis() / 1000);
        Assert.assertTrue(period - Long.valueOf(delay) <= 1);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((readLine = in.readLine()) != null)
            response.append(readLine);

        Assert.assertTrue(response.toString().contains("https://httpbin.org/delay/10"));
        Assert.assertTrue(response.toString().contains("5.35.69.45"));
        in .close();

    }

}
