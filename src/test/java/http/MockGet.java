package http;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by astrate on 03.10.2014.
 */
public class MockGet extends Get {

    private final String jsonFile;

    public MockGet(String jsonFile) {
        super("", "", "");
        this.jsonFile = jsonFile;
    }

    @Override
    public JSONObject execute(String s) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.getClass().getResource(jsonFile).getFile()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return (JSONObject)new JSONParser().parse(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
