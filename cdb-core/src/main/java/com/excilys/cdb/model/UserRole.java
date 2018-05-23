package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {

    public enum Role {
        USER(new UserRole(2L, "ROLE_USER")), ADMIN(new UserRole(1L, "ROLE_ADMIN"));
        private UserRole role;

        private Role(UserRole pRole) {
            role = pRole;
        }

        public UserRole getRole() {
            return role;
        }
    }

    @Id
    @Column(name = "ur_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "ur_label", unique = true, nullable = false)
    private String label;

    public UserRole() {
    }

    public UserRole(Long pId, String pLabel) {
        id = pId;
        label = pLabel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRole other = (UserRole) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        return result;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public void setLabel(String pLabel) {
        label = pLabel;
    }

    @Override
    public String toString() {
        return id + ":" + label;
    }
}
