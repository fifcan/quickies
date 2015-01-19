package org.fifcan.quickies.data;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by philippe on 04.01.15.
 */
@Document(collection = "votes")
public class Vote extends AbstractData {

    private Date creationTime;

    @DBRef
    private User user;

    private String userGroupSession;

    public Vote() {
        super();
    }

    public Vote(String userGroupSession, User user) {
        this();
        this.userGroupSession = userGroupSession;
        this.user = user;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserGroupSession() {
        return userGroupSession;
    }

    public void setUserGroupSession(String userGroupSession) {
        this.userGroupSession = userGroupSession;
    }

}
