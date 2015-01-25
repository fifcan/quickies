package org.fifcan.quickies.job;

import org.fifcan.quickies.twitter.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by deft on 25/01/2015.
 */
@Component
public class TwitterVoteTask {

    @Autowired
    TwitterService twitterService;

    @Scheduled(fixedRate = 5000L)
    public void loadTwitterVote() {
        twitterService.processNextTwitterVotes();
    }
}
