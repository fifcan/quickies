package org.fifcan.quickies.mongo;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.fifcan.quickies.data.UserGroup;
import org.fifcan.quickies.data.UserGroupSession;
import org.fifcan.quickies.data.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by deft on 19/01/2015.
 */
public class VoteDao {

    private static final Logger LOGGER = Logger.getLogger(VoteDao.class);

    Predicate<UserGroupSession> predicateSessionAfter(Date date) {return session -> session.getEventDate().after(date);}

    Predicate<UserGroupSession> predicateSessionBefore(Date date) {return session -> session.getEventDate().before(date);}

    @Autowired
    protected MongoTemplate mongoTemplate;

    public List<UserGroupSession> getTopFutureSessions(int limit) {

        Date todayDate = getToday();

        AggregationOutput output = countSessionsVote();

        Iterable<DBObject> results = output.results();

        return getUserGroupSessions(predicateSessionAfter(todayDate), limit, results);
    }

    public List<UserGroupSession> getTopPastSessions(int limit) {

        Date todayDate = getToday();

        AggregationOutput output = countSessionsVote();

        Iterable<DBObject> results = output.results();

        return getUserGroupSessions(predicateSessionBefore(todayDate), limit, results);
    }

    private Date getToday() {
        LocalDate todayLocalDate = LocalDate.now();

        Instant instant = todayLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    private AggregationOutput countSessionsVote() {
        DBCollection collection = mongoTemplate.getCollection("votes");

        DBObject groupFields = new BasicDBObject( "_id", "$userGroupSession");
        groupFields.put("count", new BasicDBObject( "$sum", 1));
        DBObject group = new BasicDBObject("$group", groupFields );

        DBObject sortFields = new BasicDBObject("count", -1);
        DBObject sort = new BasicDBObject("$sort", sortFields );

        return collection.aggregate(Arrays.asList(group, sort));
    }

    private List<UserGroupSession> getUserGroupSessions(Predicate<UserGroupSession> dateFilter, int limit, Iterable<DBObject> results) {
        return StreamSupport.stream(results.spliterator(), true)
                .map(result -> buildSession(result))
                .filter(dateFilter)
                .limit(limit)
                .collect(Collectors.toList());
    }

    private UserGroupSession buildSession(DBObject result) {
        UserGroupSession session = mongoTemplate.findById(result.get("_id"), UserGroupSession.class);
        Integer voteCount = (Integer) result.get("count");
        session.setVotes(voteCount);
        return session;
    }

    public void vote(Vote vote) {
        mongoTemplate.save(vote);
    }
}