package growl;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpNotification;
import com.google.code.jgntp.GntpNotificationInfo;
import stash.pullrequest.PullRequest;
import stash.pullrequest.Reviewer;

import java.util.Set;

/**
 * Created by astrate on 06.10.2014.
 */
public class GrowlPullRequestNotification {

    public static GntpNotification create(GntpNotificationInfo notificationInfo, PullRequest pullRequest) {
        return Gntp.notification(notificationInfo, createTitle(pullRequest))
                .text(createText(pullRequest))
                .callbackTarget(pullRequest.url)
                .build();
    }

    private static String createTitle(PullRequest pullRequest) {
        return pullRequest.title.substring(pullRequest.title.lastIndexOf("/") + 1, pullRequest.title.length());
    }

    private static String createText(PullRequest pullRequest) {
        return "State: " + pullRequest.state + "\nAuthor: " + pullRequest.author.name + "\nReviewers:\n" + prettifyReviewers(pullRequest.reviewers);
    }

    private static String prettifyReviewers(Set<Reviewer> reviewers) {
        StringBuilder sb = new StringBuilder();
        for (Reviewer reviewer : reviewers) {
            sb.append(reviewer.name);
            sb.append("\n- Approved: ");
            sb.append(reviewer.approved ? "Yes" : "No");
            sb.append("\n");
        }
        return sb.toString();
    }
}
