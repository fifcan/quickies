package org.fifcan.quickies.twitter;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
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

    static final String SPACE = " ";

    static final String GENEVA_JUG_TWITTER_NAME = "@GenevaJUG";

    static final String QUICKIE_TAG = "#UGQuickie";

    @Autowired
    Twitter twitter;

    @Autowired
    SessionDao sessionDao;

    @Autowired
    UserDao userDao;

    public List<Tweet> getAllTweets() {

        TimelineOperations timelineOperations = twitter.timelineOperations();

        List<Tweet> tweets = timelineOperations.getUserTimeline(GENEVA_JUG_TWITTER_NAME);

        return tweets;
    }

    public List<Vote> getTwitterVotes() {

        SearchOperations searchOperations = twitter.searchOperations();

        SearchResults searchResults = searchOperations.search(QUICKIE_TAG);

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

        List<String> tags = Splitter.on(SPACE)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(tweet.getText());

        List<Vote> votes = tags.stream()
                .filter(s -> isTwitterTag(s))
                .map(s -> sessionDao.findSessionByTwitterTag(s))
                .filter(Objects::nonNull) // If session not found no vote
                .map(s -> new Vote(s.getName(), user))
                .collect(Collectors.toList());

        return votes;
    }

    private static boolean isTwitterTag(String str) {
        return Strings.nullToEmpty(str).startsWith(HASH_TAG);
    }
}