package org.fifcan.quickies.controller;

import org.fifcan.quickies.data.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by romain on 15/12/14.
 */
@RestController
public class UserController {

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/user")
    public User addUser(
            @RequestParam(value="name", required = true) String name,
            @RequestParam(value="password", required = true) String password,
            @RequestParam(value="email", required = true) String email
    ) {
        final User user = new User(name, password, email);

        return user;

    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/user")
    public User getUser(
            @RequestParam(value="name", required = true) String name
    ) {

        return new User(name, "fff", "roro@lo.com");

    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/user")
    public User deleteUser(
            @RequestParam(value="name", required = true) String name
    ) {

        return null;

    }

}
