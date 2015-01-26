package org.fifcan.quickies.job;

import org.apache.log4j.Logger;
import org.fifcan.quickies.twitter.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by deft on 25/01/2015.
 */
@Component
public class TwitterVoteTask {

    private static final Logger LOGGER = Logger.getLogger(TwitterVoteTask.class);

    @Autowired
    TwitterService twitterService;

    @Scheduled(fixedRate = 60000L)
    public void loadTwitterVote() {
        try {
            twitterService.processNextTwitterVotes();
        } catch (Throwable t){
            LOGGER.warn(t.getMessage());
        }
    }
}
