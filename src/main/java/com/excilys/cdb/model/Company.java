package com.excilys.cdb.model;

import java.util.Optional;

public class Company {
    public static class Builder {
        private Long id;
        private String name;

        public Builder() {
        }

        public Builder(Long pId) {
            id = pId;
        }

        public Builder(String pName) {
            name = pName;
        }

        public Company build() {
            return new Company(this);
        }

        public Builder withId(Long pId) {
            id = pId;
            return this;
        }

        public Builder withName(String pName) {
            name = pName;
            return this;
        }

    }

    private Optional<Long> id;
    private Optional<String> name;

    public Company(Builder builder) {
        id = Optional.ofNullable(builder.id);
        name = Optional.ofNullable(builder.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Company other = (Company) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public Optional<Long> getId() {
        return id;
    }

    public Optional<String> getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    public void setId(Long pId) {
        id = Optional.ofNullable(pId);
    }

    public void setName(String pName) {
        name = Optional.ofNullable(pName);
    }

    @Override
    public String toString() {
        return new StringBuilder(id.toString()).append(" - ").append(name).toString();
    }
}
