package growl;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpNotificationInfo;
import core.Settings;
import stash.pullrequest.PullRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by astrate on 03.10.2014.
 */
public class GrowlClient {

    private final GntpClient gntpClient;
    private final GntpNotificationInfo pullRequestNotification;
    private final GntpNotificationInfo pullRequestWithUserNotification;
    private final Settings settings;

    public GrowlClient(Settings settings) {
        this.settings = settings;
        URI icon = null;
        try {
            icon = this.getClass().getResource("../stash-3d.png").toURI();
        } catch (URISyntaxException e) {
            icon = null;
        }
        GntpApplicationInfo info = Gntp.appInfo("Stash").icon(icon).build();
        pullRequestNotification = Gntp.notificationInfo(info, "Pull Request").build();
        pullRequestWithUserNotification = Gntp.notificationInfo(info, "Pull Request Including User").build();
        gntpClient = Gntp.client(info).forHost("localhost").build();
        gntpClient.register();
    }

    public void notify(List<PullRequest> pullRequests) {
        for (PullRequest pullRequest : pullRequests) {
            notify(pullRequest);
        }
    }

    private void notify(PullRequest pullRequest) {
        try {
            gntpClient.notify(Gntp.notification(pullRequest.user ? pullRequestWithUserNotification : pullRequestNotification, pullRequest.title.substring(pullRequest.title.lastIndexOf("/") + 1, pullRequest.title.length()))
                    .text("State: " + pullRequest.state + "\nAuthor: " + pullRequest.author + "\nReviewers: " + pullRequest.printReviewers())
                    .callbackTarget(URI.create(settings.BASE_URL + pullRequest.url))
                    .build(), 5, SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Notification interrupted");
        }
    }
}
