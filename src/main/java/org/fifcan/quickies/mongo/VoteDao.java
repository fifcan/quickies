package org.fifcan.quickies.mongo;

import com.mongodb.*;
import org.apache.log4j.Logger;
import org.fifcan.quickies.data.UserGroup;
import org.fifcan.quickies.data.UserGroupSession;
import org.fifcan.quickies.data.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by deft on 19/01/2015.
 */
public class VoteDao {

    private static final Logger LOGGER = Logger.getLogger(VoteDao.class);

    private static final Class<Vote> CLASS = Vote.class;

    @Autowired
    protected MongoTemplate mongoTemplate;

    public List<UserGroupSession> getTopFutureSessions(int limit) {

        LocalDate todayLocalDate = LocalDate.now();

        Instant instant = todayLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date todayDate = Date.from(instant);

        DBCollection collection = mongoTemplate.getCollection("votes");

       // BasicDBObject dateQuery = new BasicDBObject ("$match", new BasicDBObject("eventDate", new BasicDBObject("$gt", todayDate)));

        DBObject groupFields = new BasicDBObject( "_id", "$userGroupSession");
        groupFields.put("count", new BasicDBObject( "$sum", 1));
        DBObject group = new BasicDBObject("$group", groupFields );

        DBObject sortFields = new BasicDBObject("count", -1);
        DBObject sort = new BasicDBObject("$sort", sortFields );

        AggregationOutput output = collection.aggregate(Arrays.asList(group, sort));

        Iterable<DBObject> results = output.results();

        return getUserGroupSessions(limit, results);
    }

    public List<UserGroupSession> getTopPastSessions(int limit) {

        LocalDate todayLocalDate = LocalDate.now();

        Instant instant = todayLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date todayDate = Date.from(instant);

        DBCollection collection = mongoTemplate.getCollection("votes");

        BasicDBObject dateQuery = new BasicDBObject ("$match", new BasicDBObject("eventDate", new BasicDBObject("$lt", todayDate)));

        DBObject groupFields = new BasicDBObject( "_id", "$userGroupSession");
        groupFields.put("count", new BasicDBObject( "$sum", 1));
        DBObject group = new BasicDBObject("$group", groupFields );

        DBObject sortFields = new BasicDBObject("count", -1);
        DBObject sort = new BasicDBObject("$sort", sortFields );

        AggregationOutput output = collection.aggregate(Arrays.asList(dateQuery, group, sort));

        Iterable<DBObject> results = output.results();

        return getUserGroupSessions(limit, results);
    }

    private List<UserGroupSession> getUserGroupSessions(int limit, Iterable<DBObject> results) {
        return StreamSupport.stream(results.spliterator(), false)
                .map(result -> mongoTemplate.findById(result.get("_id"), UserGroupSession.class))
                .limit(limit)
                .collect(Collectors.toList());
    }


    public void vote(Vote vote) {
        mongoTemplate.save(vote);
    }
}