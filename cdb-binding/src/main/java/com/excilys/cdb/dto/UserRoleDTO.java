package com.excilys.cdb.dto;

public class UserRoleDTO {
    private Long id;
    private String label;

    public UserRoleDTO() {
    }

    public UserRoleDTO(Long pId, String pLabel) {
        id = pId;
        label = pLabel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String pLabel) {
        label = pLabel;
    }
}
