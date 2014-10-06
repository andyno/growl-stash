package stash;

import core.Settings;
import http.MockGet;
import junit.framework.TestCase;
import stash.pullrequest.PullRequest;

import java.util.List;

public class RestAPITest extends TestCase {

    public void testGetPullRequests() throws Exception {
        Settings.init("dummy", "pw");
        RestAPI restAPI = new RestAPI(new MockGet("../pull-requests.json"));
        List<PullRequest> pullRequests = restAPI.getPullRequests("PROJ", "repo", "TEAM");
        assertEquals(pullRequests.size(), 6);
    }
}