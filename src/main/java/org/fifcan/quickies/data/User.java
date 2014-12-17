package org.fifcan.quickies.data;

import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;

/**
 * Created by romain on 15/12/14.
 */
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;

    public User(){}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("password", password)
                .add("email", email)
                .toString();
    }
}
