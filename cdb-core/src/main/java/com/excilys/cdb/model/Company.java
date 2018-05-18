package com.excilys.cdb.model;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
    public static class Builder {

        private Long id;
        private String name;
        private String logo;

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

        public Builder withLogo(String pLogo) {
            logo = pLogo;
            return this;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ca_id")
    private Long id;
    @Column(name = "ca_name")
    private String name;
    @Column(name = "ca_picture")
    private String logo;

    public Company() {
    }

    public Company(Builder builder) {
        id = builder.id;
        name = builder.name;
        logo = builder.logo;
    }

    public Company(String pName) {
        name = pName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Company other = (Company) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getLogo() {
        return Optional.ofNullable(logo);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setLogo(String pLogo) {
        logo = pLogo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id);
            sb.append(" - ");
        }
        if (name != null) {
            sb.append(name);
        }
        return sb.toString();
    }
}
