package org.fifcan.quickies.twitter;

import org.fifcan.quickies.data.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by deft on 21/01/2015.
 */
@Service
public class TwitterService {

    @Autowired
    Twitter twitter;

    public List<Tweet> getAllTweets() {

        TimelineOperations timelineOperations = twitter.timelineOperations();

        List<Tweet> tweets = timelineOperations.getUserTimeline("@GenevaJUG");

        return tweets;
    }

    public List<Vote> getTwitterVotes() {

        SearchOperations searchOperations = twitter.searchOperations();

        SearchResults searchResults = searchOperations.search("#UGQuickie");

        return searchResults.getTweets().stream()
                .filter(t -> t.hasTags() && t.getText().toLowerCase().contains("#vote"))
                .map(t -> buildVote(t))
                .collect(Collectors.toList());

    }

    public static Vote buildVote(Tweet tweet) {

        Vote vote = null;

        // todo

        return vote;
    }
}