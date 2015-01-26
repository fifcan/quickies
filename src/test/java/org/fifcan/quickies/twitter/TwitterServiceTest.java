package org.fifcan.quickies.twitter;

import com.google.common.collect.Lists;
import org.fifcan.quickies.data.TweetProcessing;
import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.UserGroupSession;
import org.fifcan.quickies.data.Vote;
import org.fifcan.quickies.mongo.SessionDao;
import org.fifcan.quickies.mongo.TwitterProcessingDao;
import org.fifcan.quickies.mongo.UserDao;
import org.fifcan.quickies.mongo.VoteDao;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.social.linkedin.api.SearchResult;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class TwitterServiceTest {

    TwitterService twitterService = new TwitterService();

    @Test
    public void testBuildVote() throws Exception {

        Long twitterUserId = 999L;
        String id = "FakeID";

        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(id);
        Mockito.when(user.getTwitterUserId()).thenReturn(twitterUserId);

        UserGroupSession session = new UserGroupSession();
        session.setName("Wildfly");
        session.setTwitterTag("#Wildfly");

        Vote expectedVote = new Vote(session.getName(), user);

        Tweet tweet = Mockito.mock(Tweet.class);
        Mockito.when(tweet.getText()).thenReturn("#UGQuickie #Vote #Wildfly");
        Mockito.when(tweet.hasTags()).thenReturn(Boolean.TRUE);
        Mockito.when(tweet.getFromUserId()).thenReturn(twitterUserId);
        List<Tweet> tweets = Lists.newArrayList(tweet);

        SearchResults result = Mockito.mock(SearchResults.class);
        Mockito.when(result.getTweets()).thenReturn(tweets);

        SearchOperations searchOperations = Mockito.mock(SearchOperations.class);
        Mockito.when(searchOperations.search(Matchers.anyString(), Matchers.anyInt(), Matchers.anyLong(), Matchers.anyLong())).thenReturn(result);

        Twitter twitter = Mockito.mock(Twitter.class);
        ReflectionTestUtils.setField(twitterService, "twitter", twitter);
        Mockito.when(twitter.searchOperations()).thenReturn(searchOperations);

        SessionDao sessionDao = Mockito.mock(SessionDao.class);
        Mockito.when(sessionDao.findSessionByTwitterTag(session.getTwitterTag())).thenReturn(session);
        ReflectionTestUtils.setField(twitterService, "sessionDao", sessionDao);

        UserDao userDao = Mockito.mock(UserDao.class);
        Mockito.when(userDao.findUserByTwitterUserId(Matchers.anyLong())).thenReturn(user);
        ReflectionTestUtils.setField(twitterService, "userDao", userDao);

        VoteDao voteDao = Mockito.mock(VoteDao.class);
        ReflectionTestUtils.setField(twitterService, "voteDao", voteDao);

        TwitterProcessingDao twitterProcessingDao = Mockito.mock(TwitterProcessingDao.class);
        ReflectionTestUtils.setField(twitterService, "twitterProcessingDao", twitterProcessingDao);
        Mockito.when(twitterProcessingDao.getTweeterVoteProcessing()).thenReturn(TweetProcessing.createFirstTweeterVoteProcessing());

        twitterService.processNextTwitterVotes();


        Mockito.verify(voteDao).vote(Mockito.argThat(new VoteMatcher(expectedVote)));
    }

    private static class VoteMatcher extends ArgumentMatcher<Vote> {

        Vote voteExpected;

        public VoteMatcher(Vote voteExpected) {
            this.voteExpected = voteExpected;
        }

        @Override
        public boolean matches(Object o) {

            Vote vote = (Vote) o;

            return voteExpected.equals(vote);
        }
    }
}
