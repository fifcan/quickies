package org.fifcan.quickies.job;

import org.fifcan.quickies.twitter.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by deft on 25/01/2015.
 */
@EnableScheduling
public class TwitterVoteTask {

    @Autowired
    TwitterService twitterService;

    @Scheduled(fixedRate = 5000)
    public void loadTwitterVote() {
        twitterService.processNextTwitterVotes();
    }
}
