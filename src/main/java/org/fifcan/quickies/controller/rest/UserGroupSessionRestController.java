package org.fifcan.quickies.controller.rest;

import com.mongodb.WriteResult;
import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.UserGroup;
import org.fifcan.quickies.data.UserGroupSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
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

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId ")
    @RequestMapping(method = RequestMethod.POST, value = "/api/userGroupSession")
    public ResponseEntity<UserGroupSession> saveUserGroupSession(@RequestBody UserGroupSession userGroupSession) {
        mongoTemplate.save(userGroupSession);
        return new ResponseEntity<UserGroupSession>(userGroupSession, HttpStatus.OK);
    }

}
