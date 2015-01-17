package org.fifcan.quickies.controller.rest;

import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.UserGroup;
import org.fifcan.quickies.mongo.UserDao;
import org.fifcan.quickies.mongo.UserGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.PUT, value = "/api/group")
    public UserGroup createUserGroup(
            @RequestParam(value="name", required = true) String name,
            @RequestParam(value="description", required = true) String description) {

        final UserGroup userGroup = new UserGroup(name, description);
        mongoTemplate.insert(userGroup);
        return userGroup;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/group")
    public List<UserGroup> listUserGroup() {
        return userGroupDao.listGroups();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/group/{groupId}")
    public UserGroup getGroup(@PathVariable(value="groupId") String groupId) {
        return userGroupDao.findUserGroupById(groupId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/api/user/{userId}/group/{groupId}")
    public User joinGroup(
            @PathVariable(value="userId" ) String userId,
            @PathVariable(value="groupId") String groupId) {

        final User user = userDao.findUserById(userId);

        if (user == null) return null;

        final UserGroup userGroup = userGroupDao.findUserGroupById(groupId);

        if (userGroup == null) return user;

        user.joinGroup(userGroup);

        userDao.updateUser(user);

        return user;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/user/{userId}/group/{groupId}")
    public User leaveGroup(
            @PathVariable(value="userId") String userId,
            @PathVariable(value="groupId") String groupId) {

        final User user = userDao.findUserById(userId);

        if (user == null) return null;

        final UserGroup userGroup = userGroupDao.findUserGroupById(groupId);

        if (userGroup == null) return user;

        user.leaveGroup(userGroup);

        userDao.updateUser(user);

        return user;
    }
}
