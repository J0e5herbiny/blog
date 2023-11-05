package com.joe.project.dto;

import java.util.UUID;

public class UserDto {

//    private Long id;
    private UUID id;
    private String name;
    private String email;
    private String password;


    public UserDto(UUID id,
                   String name,
                   String email,
                   String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public UserDto(String name,
                   String email,
                   String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
}
