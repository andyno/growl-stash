package growl;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpNotificationInfo;
import com.google.common.io.Closeables;
import stash.pullrequest.PullRequest;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static growl.GrowlPullRequestNotification.create;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by astrate on 03.10.2014.
 */
public class GrowlClient {

    private final GntpClient gntpClient;
    private GntpNotificationInfo pullRequestNotification;
    private GntpNotificationInfo pullRequestWithUserNotification;

    public GrowlClient() {
        GntpApplicationInfo info = Gntp.appInfo("Stash").icon(getImage("/stash-3d.png")).build();
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
            gntpClient.notify(create(pullRequest.user ? pullRequestWithUserNotification : pullRequestNotification, pullRequest), 5, SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Notification interrupted");
        }
    }

    private RenderedImage getImage(String name) {
        InputStream is = getClass().getResourceAsStream(name);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            return null;
        } finally {
            Closeables.closeQuietly(is);
        }
    }
}
