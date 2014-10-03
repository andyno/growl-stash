package stash.pullrequest;

import core.Settings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astrate on 03.10.2014.
 */
public class PullRequest {
    private final Settings settings = new Settings();

    public final Long id;
    public final String title;
    public final String author;
    public final List<String> reviewers;
    public final String url;
    public final String state;
    public final boolean user;

    public PullRequest(JSONObject jsonObject) {
        this.id = (Long) jsonObject.get("id");
        this.title = (String) jsonObject.get("title");
        this.author = (String) get(get(jsonObject, "author"), "user").get("displayName");
        this.url = (String) get(jsonObject, "link").get("url");
        this.state = (String) jsonObject.get("state");
        this.reviewers = getReviewers(jsonObject);
        this.user = isUser(jsonObject);
    }

    private JSONObject get(Object jsonObject, String key) {
        return (JSONObject) ((JSONObject) jsonObject).get(key);
    }

    private List<String> getReviewers(JSONObject object) {
        List<String> reviewers = new ArrayList<String>();
        for (Object o : (JSONArray) object.get("reviewers")) {
            String name = (String) get(o, "user").get("displayName");
            reviewers.add(name);
        }
        return reviewers;
    }

    private boolean isUser(JSONObject object) {
        List<String> idents = new ArrayList<String>();
        String author = (String) get(get(object, "author"), "user").get("name");
        idents.add(author.toLowerCase());
        for (Object o : (JSONArray) object.get("reviewers")) {
            String ident = (String) get(o, "user").get("name");
            idents.add(ident.toLowerCase());
        }
        return idents.contains(settings.USERNAME.toLowerCase());
    }

    public String printReviewers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < reviewers.size(); i++) {
            sb.append(reviewers.get(i));
            if (i != reviewers.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof PullRequest) {
            PullRequest pullRequest = (PullRequest) object;
            return pullRequest.id.equals(this.id) && pullRequest.state.equals(this.state) && this.reviewers.equals(pullRequest.reviewers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
