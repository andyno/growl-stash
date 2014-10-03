package growl;

import core.Settings;
import http.MockGet;
import junit.framework.TestCase;
import stash.RestAPI;
import stash.pullrequest.PullRequest;

import java.util.List;

public class GrowlClientTest extends TestCase {

    public void testNotify() throws Exception {
        GrowlClient growlClient = new GrowlClient(new Settings());
        RestAPI restAPI = new RestAPI("user", "pswd", "");
        List<PullRequest> pullRequests = restAPI.getPullRequests(new MockGet("../pull-requests.json"), "PROJ", "repo", "TEAM");
        growlClient.notify(pullRequests);
    }
}