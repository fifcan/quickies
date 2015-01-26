package org.fifcan.quickies.controller;

import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.UserGroup;

import java.util.Set;

/**
 * Created by deft on 26/01/2015.
 */
public class UserDto {

    private String id;

    private String username;

    private String email;

    private Long twitterUserId;

    private Set<UserGroup> groups;

    public UserDto(String id, String username, String email, Long twitterUserId, Set<UserGroup> groups) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.twitterUserId = twitterUserId;
        this.groups = groups;
    }

    public static UserDto of(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getTwitterUserId(), user.getGroups());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTwitterUserId() {
        return twitterUserId;
    }

    public void setTwitterUserId(Long twitterUserId) {
        this.twitterUserId = twitterUserId;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<UserGroup> groups) {
        this.groups = groups;
    }
}
