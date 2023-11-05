package com.joe.project.dto;

public class RoleUserDto {
    private String userName;
    private String roleName;

    public RoleUserDto(String userName, String roleName) {
        this.userName = userName;
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public String getRoleName() {
        return roleName;
    }
}
