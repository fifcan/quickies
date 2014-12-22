package org.fifcan.quickies.data;

import org.springframework.data.annotation.Id;

/**
 * Created by philippe on 21.12.14.
 */
public abstract class AbstractData {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractData)) return false;

        AbstractData that = (AbstractData) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
