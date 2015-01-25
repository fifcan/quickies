package org.fifcan.quickies.twitter;

import com.google.common.base.Splitter;
import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.Vote;
import org.fifcan.quickies.mongo.SessionDao;
import org.fifcan.quickies.mongo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by deft on 21/01/2015.
 */
@Service
public class TwitterService {

    static final String VOTE_TAG = "#Vote";

    static final String HASH_TAG = "#";

    @Autowired
    Twitter twitter;

    @Autowired
    SessionDao sessionDao;

    @Autowired
    UserDao userDao;

    public List<Tweet> getAllTweets() {

        TimelineOperations timelineOperations = twitter.timelineOperations();

        List<Tweet> tweets = timelineOperations.getUserTimeline("@GenevaJUG");

        return tweets;
    }

    public List<Vote> getTwitterVotes() {

        SearchOperations searchOperations = twitter.searchOperations();

        SearchResults searchResults = searchOperations.search("#UGQuickie");

        return searchResults.getTweets().stream()
                .filter(t -> t.hasTags() && t.getText().toLowerCase().contains(VOTE_TAG))
                .map(t -> buildVotes(t))
                .flatMap(v -> v.stream())
                .collect(Collectors.toList());
    }

    public List<Vote> buildVotes(Tweet tweet) {

        Long twitterUserId = tweet.getFromUserId();

        User user = userDao.findUserByTwitterUserId(twitterUserId);

        // If the twitter user is not registered on Quickie no vote !
        if (user == null) return Collections.emptyList();

        List<String> tags = Splitter.on(" ")
                .omitEmptyStrings()
                .trimResults()
                .splitToList(tweet.getText());

        List<Vote> votes = tags.stream()
                .filter(s -> s.startsWith(HASH_TAG))
                .map(s -> extractSession(s))
                .map(s -> sessionDao.findSessionByTwitterTag(s))
                .filter(Objects::nonNull) // If session not found no vote
                .map(s -> new Vote(s.getName(), user))
                .collect(Collectors.toList());

        return votes;
    }

    private static String extractSession(String voteSession) {
        return voteSession.substring(VOTE_TAG.length() + 1);
    }
}