package stash;

import core.Settings;
import stash.pullrequest.PullRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by astrate on 03.10.2014.
 */
public class StashClient {
    private final Settings settings;

    private final Set<PullRequest> fetchedPullRequestSet = new HashSet<PullRequest>();

    private final RestAPI restAPI;

    public StashClient(Settings settings) {
        System.out.println("Connecting to Stash");
        this.settings = settings;
        restAPI = new RestAPI(settings.USERNAME, settings.PASSWORD, settings.BASE_URL);
        List<PullRequest> initPullRequest = restAPI.getPullRequests(settings.PROJECT, settings.REPOSITORY, settings.FILTER);
        fetchedPullRequestSet.addAll(initPullRequest);
    }

    public List<PullRequest> update() {
        System.out.println("Updating...");
        List<PullRequest> pullRequests = restAPI.getPullRequests(settings.PROJECT, settings.REPOSITORY, settings.FILTER);
        fetchedPullRequestSet.retainAll(pullRequests);
        pullRequests.removeAll(fetchedPullRequestSet);
        fetchedPullRequestSet.addAll(pullRequests);
        return pullRequests;
    }
}
