package org.fifcan.quickies.mongo;

import org.fifcan.quickies.data.TweetProcessing;
import org.fifcan.quickies.data.UserGroupSession;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by deft on 25/01/2015.
 */
public class TwitterProcessingDao {

    private static final Class<TweetProcessing> TWEET_PROCESSING_CLASS = TweetProcessing.class;

    protected MongoTemplate mongoTemplate;

    public TwitterProcessingDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(TweetProcessing tweetProcessing) {
        mongoTemplate.save(tweetProcessing);
    }

    public TweetProcessing getTweeterVoteProcessing() throws UsernameNotFoundException {
        TweetProcessing tweetProcessing = mongoTemplate.findOne(new Query(Criteria.where("type").is(TweetProcessing.TYPE_VOTE)), TWEET_PROCESSING_CLASS);

        if (tweetProcessing == null) return TweetProcessing.createFirstTweeterVoteProcessing();

        return tweetProcessing;
    }

}
