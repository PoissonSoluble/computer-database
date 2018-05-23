package com.excilys.cdb.dto;

public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private UserRoleDTO role;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String pLogin) {
        login = pLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pPassword) {
        password = pPassword;
    }

    public UserRoleDTO getRole() {
        return role;
    }

    public void setRole(UserRoleDTO pRole) {
        role = pRole;
    }

}