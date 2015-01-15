package org.fifcan.quickies.controller.rest;

import com.mongodb.WriteResult;
import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.UserGroup;
import org.fifcan.quickies.mongo.UserDao;
import org.fifcan.quickies.mongo.UserGroupDao;
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
 * Created by deft on 15/01/2015.
 */
@RestController
public class UserGroupRestController {

    private static final Class<UserGroup> USER_GROUP = UserGroup.class;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserGroupDao userGroupDao;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @RequestMapping(method = RequestMethod.PUT, value = "/api/usergroup")
    public UserGroup createUserGroup(
            @RequestParam(value="name", required = true) String name,
            @RequestParam(value="description", required = true) String description) {

        final UserGroup userGroup = new UserGroup(name, description);
        mongoTemplate.insert(userGroup);
        return userGroup;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/usergroup")
    public List<UserGroup> listUserGroup() {
        return userGroupDao.listGroups();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/api/addUserToGroup")
    public User addUserToGroup(
            @RequestParam(value="userId", required = true) String userId,
            @RequestParam(value="groupId", required = true) String groupId) {

        final User user = userDao.findUserById(userId);

        final UserGroup userGroup = userGroupDao.findUserGroupById(groupId);

        user.getGroups().add(userGroup);

        userDao.updateUser(user);

        return user;
    }
}
