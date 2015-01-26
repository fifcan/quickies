package org.fifcan.quickies.mongo;

import com.mongodb.WriteResult;
import org.fifcan.quickies.data.UserGroup;
import org.fifcan.quickies.data.UserGroupSession;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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

    public List<UserGroupSession> findSessionsByGroupId(String userGroupId) {
        return mongoTemplate.find(new Query(Criteria.where("userGroup").is(userGroupId)), USER_GROUP_SESSION_CLASS);
    }

    public UserGroupSession findSessionById(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), USER_GROUP_SESSION_CLASS);
    }

    public UserGroupSession findSessionByName(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), USER_GROUP_SESSION_CLASS);
    }

    public UserGroupSession findSessionByTwitterTag(String twitterTag) {
        return mongoTemplate.findOne(new Query(Criteria.where("twitterTag").is(twitterTag)), USER_GROUP_SESSION_CLASS);
    }

    public List<UserGroupSession> findNextSessions() {
        LocalDate today = LocalDate.now();
        LocalDate yesterdayLD = today.minusDays(1);

        Instant instant = yesterdayLD.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date yesterday = Date.from(instant);

        return mongoTemplate.find(new Query(Criteria.where("eventDate").gt(yesterday)), USER_GROUP_SESSION_CLASS);
    }

    public List<UserGroupSession> listSessions() {
        return mongoTemplate.findAll(USER_GROUP_SESSION_CLASS);
    }

    public void save(UserGroupSession userGroupSession) {
        mongoTemplate.save(userGroupSession);
    }

    public boolean remove(String id) {

        WriteResult result = mongoTemplate.remove(new Query(Criteria.where("id").is(id)), USER_GROUP_SESSION_CLASS);

        return result.isUpdateOfExisting();
    }
}
