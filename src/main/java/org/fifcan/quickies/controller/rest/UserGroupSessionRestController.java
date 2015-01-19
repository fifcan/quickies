package org.fifcan.quickies.controller.rest;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.UserGroupSession;
import org.fifcan.quickies.data.Vote;
import org.fifcan.quickies.mongo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by philippe on 15/12/14.
 */
@RestController
public class UserGroupSessionRestController {

    private static final Class<UserGroupSession> CLASS = UserGroupSession.class;

    private static final Logger LOGGER = Logger.getLogger(UserGroupSessionRestController.class);

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected UserDao userDao;

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession/{id}")
    public ResponseEntity<UserGroupSession> getUser(@PathVariable(value="id") String id) {
        UserGroupSession userGroupSession = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), CLASS) ;
        return new ResponseEntity<UserGroupSession>(userGroupSession, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession")
    public ResponseEntity<List<UserGroupSession>> getUsers() {
        return new ResponseEntity<List<UserGroupSession>>(mongoTemplate.findAll(CLASS), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/userGroupSession")
    public ResponseEntity<UserGroupSession> saveUserGroupSession(@RequestBody UserGroupSession userGroupSession) {
        mongoTemplate.save(userGroupSession);
        return new ResponseEntity<UserGroupSession>(userGroupSession, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession/{id}/vote")
    public void voteUserGroupSession(@PathVariable(value="id") String userGroupSessionId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        final User user = (User) userDao.loadUserByUsername(username);
        Vote vote = new Vote(userGroupSessionId, user);
        mongoTemplate.save(vote);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession/vote/top")
    public ResponseEntity<List<UserGroupSession>> getTopVotes() {
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
            UserGroupSession userGroupSession = mongoTemplate.findById(id, CLASS);
            if (userGroupSession == null){
                LOGGER.warn("userGroupSession not found : " + id);
                continue;
            }
            Integer count = (Integer) result.get("count");
            userGroupSession.setVotes(count);
            if (Calendar.getInstance().getTime().before(userGroupSession.getEventDate())){
                if(topPastSessions.size() < 3){
                    topPastSessions.add(userGroupSession);
                    LOGGER.info("ajout de " + id + " dans le top PAST");
                }
            } else {
                if(topFutureSessions.size() < 3){
                    topFutureSessions.add(userGroupSession);
                    LOGGER.info("ajout de " + id + " dans le top FUTURE");
                }
            }
            if(topFutureSessions.size() == 3 && topPastSessions.size() == 3){
                break;
            }
        }
        topFutureSessions.addAll(topPastSessions);
        return new ResponseEntity<List<UserGroupSession>>(topFutureSessions, HttpStatus.OK);
    }

}
