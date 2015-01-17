package org.fifcan.quickies.controller.rest;

import org.fifcan.quickies.data.UserGroupSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by philippe on 15/12/14.
 */
@RestController
public class UserGroupSessionRestController {

    private static final Class<UserGroupSession> CLASS = UserGroupSession.class;

    @Autowired
    protected MongoTemplate mongoTemplate;

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

//    @Autowired
//    private UserGroupSessionDAO userGroupSessionDAO;
//
//    @Autowired
//    private VoteDAO voteDAO;
//
//    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession/{id}")
//    public ResponseEntity<UserGroupSession> getUserGroupSession(@PathVariable(value="id") String id) {
//        UserGroupSession userGroupSession = userGroupSessionDAO.findById(id);
//        return new ResponseEntity<UserGroupSession>(userGroupSession, HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession")
//    public ResponseEntity<List<UserGroupSession>> getUserGroupSessions() {
//        return new ResponseEntity<List<UserGroupSession>>(userGroupSessionDAO.findAll(), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.POST, value = "/api/userGroupSession")
//    public ResponseEntity<UserGroupSession> saveUserGroupSession(@RequestBody UserGroupSession userGroupSession) {
//        userGroupSessionDAO.create(userGroupSession);
//        return new ResponseEntity<UserGroupSession>(userGroupSession, HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession/{id}/vote")
//    public void voteUserGroupSession(@PathVariable(value="id") String userGroupSessionId) {
//        Vote vote = new Vote(userGroupSessionId);
//        voteDAO.create(vote);
//    }

}
