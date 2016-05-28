package salarycalculation.utils;

import org.apache.commons.lang.builder.HashCodeBuilder;

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

        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this.getId());
    }

}
