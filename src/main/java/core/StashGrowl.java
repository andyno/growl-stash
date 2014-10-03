package core;

import growl.GrowlClient;
import stash.StashClient;
import stash.pullrequest.PullRequest;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by astrate on 03.10.2014.
 */
public class StashGrowl {

    public static void main(String[] args) {
        Settings settings = new Settings();
        StashClient stashClient = new StashClient(settings);
        GrowlClient growlClient = new GrowlClient(settings);
        while (true) {
            try {
                sleep(settings.POLL_TIME);
                List<PullRequest> newPullRequests = stashClient.update();
                growlClient.notify(newPullRequests);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
