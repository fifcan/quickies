package org.fifcan.quickies.mongo;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Created by romain on 17/12/14.
 */
@Configuration
public class SpringMongoConfiguration {

    public @Bean UserDao getUserDao() throws Exception {return new UserDao(getMongoTemplate());};

    public @Bean
    MongoDbFactory getMongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient("localhost",27017), "quickies");
    }

    public @Bean
    MongoTemplate getMongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(getMongoDbFactory());
        return mongoTemplate;
    }
}