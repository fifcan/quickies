package org.fifcan.quickies.controller.rest;

import com.mongodb.WriteResult;
import org.fifcan.quickies.data.User;
import org.fifcan.quickies.mongo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private UserDao userDao;

    @RequestMapping(method = RequestMethod.POST, value = "/api/user")
    public User addUser(
            @RequestParam(value="name", required = true) String name,
            @RequestParam(value="password", required = true) String password,
            @RequestParam(value="email", required = true) String email) {

        ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
        final User user = new User(name, passwordEncoder.encodePassword(password, null), email);

        userDao.save(user);

        return user;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/user")
    public User getUser(@RequestParam(value="name", required = true) String name) {

        return userDao.findUserByName(name);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/users")
    public List<User> getUsers() {

        return userDao.listUsers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId ")
    @RequestMapping(method = RequestMethod.DELETE, value = "/api/user")
    public Boolean deleteUser(@RequestParam(value="name", required = true) String name) {

        return userDao.deleteUser(name);
    }
}
