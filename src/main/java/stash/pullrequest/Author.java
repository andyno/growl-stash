package stash.pullrequest;

import org.json.simple.JSONObject;

/**
 * Created by astrate on 06.10.2014.
 */
public class Author extends User {
    public Author(JSONObject authorJson) {
        super((JSONObject)authorJson.get("user"));
    }
}
