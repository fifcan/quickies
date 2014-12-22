package org.fifcan.quickies.data;

import java.util.Date;

/**
 * Created by philippe on 21.12.14.
 */
public class UserGroupSession extends AbstractData {

    private String name;
    private String description;
    private String userGroup;
    private Date startTime;
    private Date endTime;

    public UserGroupSession() {
        super();
    }

    public UserGroupSession(String name, String description, String userGroup) {
        this();
        this.name = name;
        this.description = description;
        this.userGroup = userGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
