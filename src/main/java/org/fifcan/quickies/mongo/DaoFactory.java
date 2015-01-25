package org.fifcan.quickies.mongo;

import com.mongodb.MongoClient;
import org.fifcan.quickies.mongo.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Created by romain on 17/12/14.
 */
@Configuration
public class DaoFactory {

    @Bean
    public UserGroupDao getUserGroupDao() throws Exception {
        return new UserGroupDao(getMongoTemplate());
    };

    @Bean
    public SessionDao getSessionDao() throws Exception {
        return new SessionDao(getMongoTemplate());
    };

    @Bean
    public UserDao getUserDao() throws Exception {
        return new UserDao(getMongoTemplate());
    };

    @Bean
    public VoteDao getVoteDao() throws Exception {
        return new VoteDao();
    };

    @Bean
    public CommentDao getCommentDao() throws Exception {
        return new CommentDao(getMongoTemplate());
    };

    @Bean
    public MongoDbFactory getMongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient("localhost",27017), "quickies");
    }

    @Bean
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(getMongoDbFactory());
    }
}
