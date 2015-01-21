package org.fifcan.quickies.twitter;

import com.google.common.base.Splitter;
import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.Vote;
import org.fifcan.quickies.mongo.SessionDao;
import org.fifcan.quickies.mongo.UserDao;
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

    static final String VOTE_TAG = "#Vote";

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

        String twitterUser = tweet.getFromUser();

        // todo get user from tweet
        User user = userDao.findUserByName(twitterUser);

        List<String> tags = Splitter.on(" ")
                .omitEmptyStrings()
                .trimResults()
                .splitToList(tweet.getText());

        List<Vote> votes = tags.stream()
                .filter(s -> s.startsWith(VOTE_TAG))
                .map(s -> extractSession(s))
                // Load session ? .map(s -> sessionDao.findSessionByName(s))
                .map(s -> new Vote(s, user))
                .collect(Collectors.toList());

        return votes;
    }

    private static String extractSession(String voteSession) {
        return voteSession.substring(VOTE_TAG.length() + 1);
    }
}