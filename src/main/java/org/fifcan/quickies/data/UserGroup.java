package org.fifcan.quickies.data;

import com.google.common.base.Objects;

/**
 * Created by philippe on 21.12.14.
 */
public class UserGroup extends AbstractData {

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
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .toString();
    }
}
