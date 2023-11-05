package com.joe.project.dto;

import java.util.UUID;

public class RoleDto {
//    private Long id;

    private UUID id;
    private String roleName;

    public RoleDto(UUID id,
                   String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public UUID getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
