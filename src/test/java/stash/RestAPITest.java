package stash;

import http.MockGet;
import junit.framework.TestCase;
import stash.pullrequest.PullRequest;

import java.util.List;

public class RestAPITest extends TestCase {

    public void testGetPullRequests() throws Exception {
        RestAPI restAPI = new RestAPI("user", "pw", "");
        List<PullRequest> pullRequests = restAPI.getPullRequests(new MockGet("../pull-requests.json"), "PROJ", "repo", "TEAM");
        assertEquals(pullRequests.size(), 6);
    }
}