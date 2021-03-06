package org.fifcan.quickies.data;

import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by philippe on 21.12.14.
 */
@Document(collection = "userGroups")
public class UserGroup extends AbstractData {

    @Indexed(unique = true)
    private String name;

    private String description;

    public UserGroup() {
        super();
    }

    public UserGroup(String name, String description) {
        this();
        this.name = name;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {

        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        final UserGroup other = (UserGroup) o;

        return Objects.equal(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(this.getId());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .toString();
    }
}
