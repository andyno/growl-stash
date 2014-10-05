package stash;

import http.Get;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import stash.pullrequest.PullRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by astrate on 03.10.2014.
 */
public class RestAPI {

    private final Get get;

    public RestAPI(String username, String password, String baseUrl) {
        get = new Get(username, password, baseUrl);
    }

    public List<PullRequest> getPullRequests(String project, String repository, String filter) {
        return getPullRequests(get, project, repository, filter);
    }

    public List<PullRequest> getPullRequests(Get get, String project, String repository, String filter) {
        try {
            JSONObject jsonObject = get.execute(String.format("rest/api/1.0/projects/%s/repos/%s/pull-requests", project, repository));
            List<PullRequest> pullRequests = new ArrayList<PullRequest>();
            for (Object object : (JSONArray) jsonObject.get("values")) {
                PullRequest pullRequest = new PullRequest((JSONObject) object);
                if (pullRequest.title.toLowerCase().contains(filter.toLowerCase())) {
                    pullRequests.add(pullRequest);
                }
            }
            return pullRequests;
        } catch (IOException e) {
            System.err.println("API Call failed:");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("API Call failed:");
            e.printStackTrace();
        }
        return new ArrayList<PullRequest>(0);
    }
}
