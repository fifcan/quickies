package org.fifcan.quickies.data;

import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by romain on 15/12/14.
 */
@Document(collection = "users")
public class User extends AbstractData implements UserDetails {

    @Indexed(unique = true)
    private String username;

    private String password;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private Long twitterUserId;

    @DBRef
    private Set<UserGroup> groups;

    private Set<GrantedAuthority> grantedAuthorities;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

    public User(){}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.groups = new HashSet<UserGroup>();
        this.grantedAuthorities = new HashSet<GrantedAuthority>();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<UserGroup> groups) {
        this.groups = groups;
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Set<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Long getTwitterUserId() {
        return twitterUserId;
    }

    public void setTwitterUserId(Long twitterUserId) {
        this.twitterUserId = twitterUserId;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("email", email)
                .add("groups", groups)
                .toString();
    }


    public void joinGroup(UserGroup groupJoined) {

        if (groupJoined == null) return;

        if (groups == null) {
            groups = new HashSet<>();
        }
        groups.add(groupJoined);
    }

    public void leaveGroup(UserGroup groupToLeave) {

        if (groupToLeave == null) return;

        if (groups == null) return;

        groups.remove(groupToLeave);
    }

    public void grantAuthority(GrantedAuthority grantedAuthority) {

        if (grantedAuthority == null) return;

        if (grantedAuthorities == null) {
            grantedAuthorities = new HashSet<>();
        }
        grantedAuthorities.add(grantedAuthority);
    }

    public void removeAuthority(GrantedAuthority grantedAuthority) {

        if (grantedAuthority == null) return;

        if (grantedAuthorities == null) return;

        grantedAuthorities.remove(grantedAuthority);
    }
}
