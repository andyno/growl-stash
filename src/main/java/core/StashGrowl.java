package core;

import growl.GrowlClient;
import gui.LoginDialog;
import stash.StashClient;
import stash.pullrequest.PullRequest;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by astrate on 03.10.2014.
 */
public class StashGrowl {

    public static void main(String[] args) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.setVisible(true);
        if(loginDialog.isSucceeded()) {
            Settings.init(loginDialog.getUsername(), loginDialog.getPassword());
        } else {
            return;
        }
        Settings settings = Settings.getInstance();
        StashClient stashClient = new StashClient(settings);
        GrowlClient growlClient = new GrowlClient();
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
