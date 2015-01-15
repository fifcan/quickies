package org.fifcan.quickies.mongo;

import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.UserGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by deft on 03/01/2015.
 */
public class UserGroupDao {

    private static final Class<UserGroup> USER_GROUP_CLASS = UserGroup.class;

    protected MongoTemplate mongoTemplate;

    public UserGroupDao( MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public UserGroup findUserGroupById(String id) throws UsernameNotFoundException {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), USER_GROUP_CLASS);
    }

    public List<UserGroup> listGroups() {
        return mongoTemplate.findAll(USER_GROUP_CLASS);
    }
}
