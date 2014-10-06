package stash.pullrequest;

import org.json.simple.JSONObject;

/**
 * Created by astrate on 06.10.2014.
 */
public class Reviewer extends User {

    public final boolean approved;

    public Reviewer(JSONObject reviewerJson) {
        super((JSONObject)reviewerJson.get("user"));
        this.approved = (Boolean)reviewerJson.get("approved");
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Reviewer) {
            Reviewer object = (Reviewer)o;
            return super.equals(object) && object.approved == this.approved;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + (approved ? 1:0);
    }
}
