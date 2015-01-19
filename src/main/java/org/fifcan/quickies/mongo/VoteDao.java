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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by deft on 19/01/2015.
 */
public class VoteDao {

    private static final Logger LOGGER = Logger.getLogger(VoteDao.class);

    private static final Class<Vote> CLASS = Vote.class;

    @Autowired
    protected MongoTemplate mongoTemplate;

    public List<UserGroupSession> getTopUserGroupSession() {

        DBCollection collection = mongoTemplate.getCollection("votes");
        DBObject groupFields = new BasicDBObject( "_id", "$userGroupSession");
        groupFields.put("count", new BasicDBObject( "$sum", 1));
        DBObject group = new BasicDBObject("$group", groupFields );
        DBObject sortFields = new BasicDBObject("count", -1);
        DBObject sort = new BasicDBObject("$sort", sortFields );

        final List<UserGroupSession> topFutureSessions = new ArrayList<>(6);
        final List<UserGroupSession> topPastSessions = new ArrayList<>(6);

        AggregationOutput output = collection.aggregate(Arrays.asList(group, sort));
        Iterable<DBObject> results = output.results();
        for(DBObject result : results){
            Object id = result.get("_id");
            UserGroupSession userGroupSession = mongoTemplate.findById(id, UserGroupSession.class);
            if (userGroupSession == null){
                LOGGER.warn("userGroupSession not found : " + id);
                continue;
            }
            Integer count = (Integer) result.get("count");
            userGroupSession.setVotes(count);


            if (Calendar.getInstance().getTime().before(userGroupSession.getEventDate())){
                if(topPastSessions.size() < 3){
                    topPastSessions.add(userGroupSession);
                }
            } else {
                if(topFutureSessions.size() < 3){
                    topFutureSessions.add(userGroupSession);
                }
            }
            if(topFutureSessions.size() == 3 && topPastSessions.size() == 3){
                break;
            }
        }
        topFutureSessions.addAll(topPastSessions);

        return topFutureSessions;
    }


    public void vote(Vote vote) {
        mongoTemplate.save(vote);
    }
}
