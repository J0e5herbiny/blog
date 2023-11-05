package com.joe.project.dto;

import com.joe.project.entity.Role;

import java.util.Collection;
import java.util.UUID;

public class UserRoleDto {
//    private Long id;
    private UUID id;
    private String name;
    private String email;
    private String password;
    private Collection<Role> roles;

    public UserRoleDto(UUID id,
                       String name,
                       String email,
                       String password,
                       Collection<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

}
