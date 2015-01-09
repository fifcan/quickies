package org.fifcan.quickies.data;

import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by philippe on 04.01.15.
 */
@Document(collection = "comments")
public class Comment extends AbstractData {

    private Date creationTime;
    private String author;
    private String comment;
    private String userGroupSession;

    public Comment() {
        super();
    }

    public Comment(String userGroupSession) {
        this();
        this.userGroupSession = userGroupSession;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUserGroupSession() {
        return userGroupSession;
    }

    public void setUserGroupSession(String userGroupSession) {
        this.userGroupSession = userGroupSession;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
