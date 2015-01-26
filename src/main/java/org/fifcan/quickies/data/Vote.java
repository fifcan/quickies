package org.fifcan.quickies.data;

import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Vote other = (Vote) obj;

        return Objects.equal(this.user.getId(), other.getUser().getId())
                && Objects.equal(this.userGroupSession, other.userGroupSession)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.user.getId(), this.userGroupSession);
    }
}
