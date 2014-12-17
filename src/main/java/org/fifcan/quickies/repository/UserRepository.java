package org.fifcan.quickies.repository;

import org.fifcan.quickies.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by philippe on 16.12.14.
 */
@RepositoryRestResource
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(@Param("username") String username);
    User findByEmail(@Param("email") String email);

}
