package stash.pullrequest;

import core.Settings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by astrate on 03.10.2014.
 */
public class PullRequest {

    public final Long id;
    public final String title;
    public final Author author;
    public final Set<Reviewer> reviewers;
    public final String state;
    public final URI url;
    public final boolean user;

    public PullRequest(JSONObject jsonObject) {
        this.id = (Long) jsonObject.get("id");
        this.title = (String) jsonObject.get("title");
        this.author = new Author((JSONObject) jsonObject.get("author"));
        String relLink = (String) ((JSONObject) jsonObject.get("link")).get("url");
        this.url = URI.create(Settings.getInstance().BASE_URL + relLink);
        this.state = (String) jsonObject.get("state");
        this.reviewers = parseReviewers(jsonObject);
        this.user = isUser();
    }

    private Set<Reviewer> parseReviewers(JSONObject object) {
        Set<Reviewer> reviewers = new HashSet<Reviewer>();
        for (Object o : (JSONArray) object.get("reviewers")) {
            reviewers.add(new Reviewer((JSONObject) o));
        }
        return reviewers;
    }

    private boolean isUser() {
        boolean isUserReviewer = false;
        for (Reviewer reviewer : reviewers) {
            if (reviewer.ident.equals(Settings.getInstance().USERNAME.toLowerCase())) {
                isUserReviewer = true;
            }
        }
        return isUserReviewer || author.ident.equals(Settings.getInstance().USERNAME.toLowerCase());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof PullRequest) {
            PullRequest pullRequest = (PullRequest) object;
            return pullRequest.id.equals(this.id)
                    && pullRequest.author.equals(this.author)
                    && pullRequest.state.equals(this.state)
                    && this.reviewers.equals(pullRequest.reviewers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
