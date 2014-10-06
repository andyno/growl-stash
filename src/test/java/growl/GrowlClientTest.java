package growl;

import core.Settings;
import http.MockGet;
import junit.framework.TestCase;
import stash.RestAPI;
import stash.pullrequest.PullRequest;

import java.util.List;

public class GrowlClientTest extends TestCase {

    public void testNotify() throws Exception {
        Settings.init("dummy", "pw");
        GrowlClient growlClient = new GrowlClient();
        RestAPI restAPI = new RestAPI(new MockGet("../pull-requests.json"));
        List<PullRequest> pullRequests = restAPI.getPullRequests("PROJ", "repo", "TEAM");
        growlClient.notify(pullRequests);
    }
}