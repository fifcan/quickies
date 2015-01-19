package org.fifcan.quickies.controller.rest;

import org.fifcan.quickies.data.UserGroupSession;
import org.fifcan.quickies.mongo.SessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by philippe on 15/12/14.
 */
@RestController
public class UserGroupSessionRestController {

    @Autowired
    protected SessionDao sessionDao;

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession/{id}")
    public ResponseEntity<UserGroupSession> getUser(@PathVariable(value="id") String id) {
        UserGroupSession userGroupSession = sessionDao.findSessionById(id);
        return new ResponseEntity<UserGroupSession>(userGroupSession, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession")
    public ResponseEntity<List<UserGroupSession>> getUsers() {
        List<UserGroupSession> sessions = sessionDao.listSessions();
        return new ResponseEntity<List<UserGroupSession>>(sessions, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId ")
    @RequestMapping(method = RequestMethod.POST, value = "/api/userGroupSession")
    public ResponseEntity<UserGroupSession> saveUserGroupSession(@RequestBody UserGroupSession userGroupSession) {
        sessionDao.save(userGroupSession);
        return new ResponseEntity<UserGroupSession>(userGroupSession, HttpStatus.OK);
    }

}
