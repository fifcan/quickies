package org.fifcan.quickies.data;

import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by philippe on 21.12.14.
 */
@Document(collection = "UserGroupSessions")
public class UserGroupSession extends AbstractData {

    private String name;
    private String description;
    private String userGroup;
    private Date eventDate;
    private String twitterTag;
    private transient Integer votes;

    public UserGroupSession() {
        super();
    }

    public UserGroupSession(String name, String description, String userGroup, Date eventDate) {
        this();
        this.name = name;
        this.description = description;
        this.userGroup = userGroup;
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public String getName() {
        return name;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getTwitterTag() {
        return twitterTag;
    }

    public void setTwitterTag(String twitterTag) {
        this.twitterTag = twitterTag;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .add("userGroup", userGroup)
                .add("eventDate", eventDate)
                .toString();
    }


}
