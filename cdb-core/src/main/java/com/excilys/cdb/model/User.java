package com.excilys.cdb.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User implements UserDetails{

    public static class Builder {
        
        private Long id;
        private String login;
        private String password;
        private UserRole role;
        private String token;

        public Builder() {
        }

        public Builder(Long pId) {
            id = pId;
        }

        public User build() {
            return new User(this);
        }

        public Builder withId(Long pId) {
            id = pId;
            return this;
        }
        
        public Builder withLogin(String pLogin) {
            login = pLogin;
            return this;
        }

        public Builder withPassword(String pPassword) {
            password = pPassword;
            return this;
        }

        public Builder withRole(UserRole pRole) {
            role = pRole;
            return this;
        }

        public Builder withToken(String pToken) {
            token = pToken;
            return this;
        }
    }

    @Id
    @Column(name = "us_id", unique = true, nullable = false)
    private Long id;
    
    @Column(name = "us_login", unique = true, nullable = false)
    private String login;

    @Column(name = "us_password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "ur_id", nullable = false)
    private UserRole role;
    
    @Transient
    private String token;

    public User() {
    }

    public User(Builder builder) {
        id = builder.id;
        login = builder.login;
        password = builder.password;
        role = builder.role;
        token = builder.token;
    }

    public User(Long pId, String pLogin, String pPassword, UserRole pRole) {
        id = pId;
        login = pLogin;
        password = pPassword;
        role = pRole;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        return true;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.getLabel()));
        return roles;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        return result;
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

    public void setId(Long pId) {
        id = pId;
    }

    public void setLogin(String pLogin) {
        login = pLogin;
    }

    public void setPassword(String pPassword) {
        password = pPassword;
    }

    public void setRole(UserRole pRole) {
        role = pRole;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return login;
    }

}
