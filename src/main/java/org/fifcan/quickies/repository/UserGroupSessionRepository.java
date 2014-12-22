package org.fifcan.quickies.repository;

import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.UserGroupSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by philippe on 16.12.14.
 */
@RepositoryRestResource
public interface UserGroupSessionRepository extends MongoRepository<UserGroupSession, String> {

    UserGroupSession findByName(@Param("name") String name);
    UserGroupSession findByUserGroup(@Param("userGroup") String userGroup);
    List<UserGroupSession> findAll();
}
