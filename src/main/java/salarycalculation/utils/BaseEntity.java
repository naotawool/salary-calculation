package salarycalculation.utils;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class BaseEntity<T> implements Entity<T> {

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!this.getClass().isInstance(obj)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Entity<T> other = (Entity<T>) obj;

        if (this.getId() == null && other.getId() == null) {
            return true;
        }

        if (this.getId() == null) {
            return false;
        }

        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this.getId());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
