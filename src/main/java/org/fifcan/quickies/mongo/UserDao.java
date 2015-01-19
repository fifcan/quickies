package org.fifcan.quickies.mongo;

import com.mongodb.WriteResult;
import org.fifcan.quickies.data.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by deft on 03/01/2015.
 */
public class UserDao implements UserDetailsService {

    private static final Class<User> USER = User.class;

    protected MongoTemplate mongoTemplate;

    public UserDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return mongoTemplate.findOne(new Query(Criteria.where("username").is(name)), USER);
    }

    public User findUserByName(String name) throws UsernameNotFoundException {
        return mongoTemplate.findOne(new Query(Criteria.where("username").is(name)), USER);
    }

    public User findUserById(String id) throws UsernameNotFoundException {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), USER);
    }

    public void updateUser(User user) {
        mongoTemplate.save(user);
    }


    public void save(User user) {
        mongoTemplate.save(user);
    }

    public List<User> listUsers() {
        return mongoTemplate.findAll(USER);
    }

    public boolean disableUser(String name) {

        User user = findUserByName(name);

        if (user == null) return false;

        user.setEnabled(false);

        save(user);

        return true;
    }

    public boolean deleteUser(String name) {

        WriteResult result = mongoTemplate.remove(new Query(Criteria.where("username").is(name)), USER);

        return result.isUpdateOfExisting();
    }
}
