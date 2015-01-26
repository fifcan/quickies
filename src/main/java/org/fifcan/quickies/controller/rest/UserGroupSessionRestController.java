package org.fifcan.quickies.controller.rest;

import org.apache.log4j.Logger;
import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.UserGroupSession;
import org.fifcan.quickies.data.Vote;
import org.fifcan.quickies.mongo.UserDao;
import org.fifcan.quickies.mongo.VoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.fifcan.quickies.mongo.SessionDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by philippe on 15/12/14.
 */
@RestController
public class UserGroupSessionRestController {

    private static final Class<UserGroupSession> CLASS = UserGroupSession.class;

    private static final Logger LOGGER = Logger.getLogger(UserGroupSessionRestController.class);

    @Autowired
    protected SessionDao sessionDao;

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected VoteDao voteDao;

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession/{id}")
    public ResponseEntity<UserGroupSession> getUser(@PathVariable(value="id") String id) {
        UserGroupSession userGroupSession = sessionDao.findSessionById(id);
        return new ResponseEntity<UserGroupSession>(userGroupSession, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession")
    public ResponseEntity<List<UserGroupSession>> getSessions() {
        List<UserGroupSession> sessions = sessionDao.listSessions();
        return new ResponseEntity<List<UserGroupSession>>(sessions, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession/next")
    public ResponseEntity<List<UserGroupSession>> getNextSession() {
        List<UserGroupSession> sessions = sessionDao.findNextSessions();
        return new ResponseEntity<List<UserGroupSession>>(sessions, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId ")
    @RequestMapping(method = RequestMethod.POST, value = "/api/userGroupSession")
    public ResponseEntity<UserGroupSession> saveUserGroupSession(@RequestBody UserGroupSession userGroupSession) {
        sessionDao.save(userGroupSession);
        return new ResponseEntity<UserGroupSession>(userGroupSession, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (principal != 'anonymousUser' and principal.id == #userId)")
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
        voteDao.vote(new Vote(userGroupSessionId, user));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/userGroupSession/vote/top")
    public ResponseEntity<Map<String, List<UserGroupSession>>> getTopVotes() {

        Map<String, List<UserGroupSession>> topSessions = new HashMap<>();
        topSessions.put("topNext", voteDao.getTopFutureSessions(3));
        topSessions.put("topPrevious", voteDao.getTopPastSessions(3));

        return new ResponseEntity<Map<String, List<UserGroupSession>>>(topSessions, HttpStatus.OK);
    }

}
