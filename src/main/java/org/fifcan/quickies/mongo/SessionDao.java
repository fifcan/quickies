package org.fifcan.quickies.mongo;

import org.fifcan.quickies.data.UserGroup;
import org.fifcan.quickies.data.UserGroupSession;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by deft on 19/01/2015.
 */
public class SessionDao {

    private static final Class<UserGroupSession> USER_GROUP_SESSION_CLASS = UserGroupSession.class;

    protected MongoTemplate mongoTemplate;

    public SessionDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public UserGroupSession findSessionById(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), USER_GROUP_SESSION_CLASS) ;
    }

    public List<UserGroupSession> listSessions() {
        return mongoTemplate.findAll(USER_GROUP_SESSION_CLASS);
    }

    public void save(UserGroupSession userGroupSession) {
        mongoTemplate.save(userGroupSession);
    }
}
