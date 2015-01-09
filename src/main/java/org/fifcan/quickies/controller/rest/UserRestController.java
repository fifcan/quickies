package org.fifcan.quickies.controller.rest;

import com.mongodb.WriteResult;
import org.fifcan.quickies.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by romain on 15/12/14.
 */
@RestController
public class UserRestController {

    private static final Class<User> USER = User.class;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @RequestMapping(method = RequestMethod.PUT, value = "/rest/user")
    public User addUser(
            @RequestParam(value="name", required = true) String name,
            @RequestParam(value="password", required = true) String password,
            @RequestParam(value="email", required = true) String email) {

        ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
        final User user = new User(name, passwordEncoder.encodePassword(password, null), email);
        mongoTemplate.save(user);
        return user;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rest/user")
    public User getUser(@RequestParam(value="name", required = true) String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("username").is(name)), USER) ;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rest/users")
    public List<User> getUsers() {
        return mongoTemplate.findAll(USER);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/rest/user")
    public Boolean deleteUser(@RequestParam(value="name", required = true) String name) {
        WriteResult result = mongoTemplate.remove(new Query(Criteria.where("username").is(name)), USER);
        return result.isUpdateOfExisting();
    }
}
