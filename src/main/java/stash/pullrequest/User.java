package stash.pullrequest;

import org.json.simple.JSONObject;

/**
 * Created by astrate on 06.10.2014.
 */
public class User {

    public final String name;
    public final String ident;

    public User(JSONObject userJson) {
        this.name = (String)userJson.get("displayName");
        this.ident = ((String)userJson.get("name")).toLowerCase();

    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof User) {
            User object = (User)o;
            return this.ident.equals(object.ident) && this.name.equals(object.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ident.hashCode() + name.hashCode();
    }
}
