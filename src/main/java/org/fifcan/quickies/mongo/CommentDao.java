package org.fifcan.quickies.mongo;

import org.fifcan.quickies.data.Comment;
import org.fifcan.quickies.data.UserGroupSession;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by deft on 19/01/2015.
 */
public class CommentDao {

    private static final Class<Comment> COMMENT_CLASS = Comment.class;

    protected MongoTemplate mongoTemplate;

    public CommentDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(Comment comment) {
        mongoTemplate.save(comment);
    }

}
