package org.fifcan.quickies.repository;

import org.fifcan.quickies.data.UserGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by philippe on 21.12.14.
 */
@RepositoryRestResource
public interface UserGroupRepository  extends MongoRepository<UserGroup, String> {

}
