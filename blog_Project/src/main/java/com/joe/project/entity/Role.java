package com.joe.project.entity;

import com.joe.project.dto.RoleDto;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table
@Builder
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    public Role() {
    }

    public Role(String roleName) {
        this.name = roleName;
    }

    public String getRoleName() {
        return name;
    }

    public void setRoleName(String roleName) {
        this.name = roleName;
    }

    public RoleDto roleDto(){
        return new RoleDto(id, name);
    }

}
