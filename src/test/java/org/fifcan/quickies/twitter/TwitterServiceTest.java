package org.fifcan.quickies.twitter;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.social.twitter.api.Tweet;

import static org.junit.Assert.*;

public class TwitterServiceTest {

    @Test
    public void testBuildVote() throws Exception {

        Tweet tweet = Mockito.mock(Tweet.class);

        Mockito.when(tweet.getText()).thenReturn("#UGQuickie #Vote#Wildfly");

        // #UGQuickie #Vote
        // #UGQuickie #Vote #Wildfly


        new TwitterService().buildVotes(tweet);


    }
}
