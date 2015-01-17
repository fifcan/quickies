package org.fifcan.quickies.data;

import com.google.common.base.Objects;
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

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        final AbstractData other = (AbstractData) o;

        return Objects.equal(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
