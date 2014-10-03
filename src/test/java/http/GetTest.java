package http;

import junit.framework.TestCase;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class GetTest extends TestCase {

    public static final String USER = "user";
    public static final String PASSWD = "passwd";

    public void testGet() throws IOException, ParseException {
        Get get = new Get(USER, PASSWD, "http://httpbin.org/");
        JSONObject jsonObject = get.execute("basic-auth/user/passwd");
        assertTrue((Boolean)jsonObject.get("authenticated"));
        assertEquals(jsonObject.get("user"), USER);
    }
}