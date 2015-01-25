package org.fifcan.quickies.data;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by deft on 25/01/2015.
 */
@Document(collection = "tweetProcessing")
public class TweetProcessing extends AbstractData {

    public static String TYPE_VOTE = "vote";

    @Indexed(unique = true)
    String type;

    Long toTweetId;

    public static TweetProcessing createFirstTweeterVoteProcessing() {
        TweetProcessing processing = new TweetProcessing();
        processing.setToTweetId(0L);
        processing.setType(TYPE_VOTE);
        return processing;
    }

    public Long getToTweetId() {
        return toTweetId;
    }

    public void setToTweetId(Long toTweetId) {
        this.toTweetId = toTweetId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
