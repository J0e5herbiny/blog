package com.joe.project.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.project.dto.RoleDto;
import com.joe.project.dto.RoleUserDto;
import com.joe.project.dto.UserDto;
import com.joe.project.dto.UserRoleDto;
import com.joe.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userService.readUsers();
    }

    @GetMapping(path = "/{userId}")
    public UserDto getUser(@PathVariable("userId") UUID id){
        return userService.readUser(id);
    }



    @PostMapping
    public void postUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
    }

    @PutMapping(path = "/{userId}")
    public void putUser(@PathVariable("userId") UUID id,
                        @RequestBody UserDto userDto){
        userService.updateUser(id, userDto);
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable("userId") UUID id){
        userService.deleteUser(id);
    }

    @PostMapping("/roles/assign")
    public void assignUserRole(@RequestBody RoleUserDto roleUserDto){
        userService.userRole(
                roleUserDto.getUserName(),
                roleUserDto.getRoleName());
    }

    @GetMapping("/roles")
    public List<UserRoleDto> getUsersWithRoles() {
        return userService.readUsersRole();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserDto user = userService.readUserByUserName(username);

                String access_token = JWT.create()
                        .withSubject("username")
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }
            catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            throw new RuntimeException("Refresh token is missing ");
        }
    }

    @PostMapping("/roles/save")
    public RoleDto saveRole(@Valid @RequestBody RoleDto roleDto) {
        return userService.saveRole(roleDto);
    }

    @PostMapping("/roles/assign")
    public void assignRoleToUser(@Valid @RequestBody RoleUserDto roleToUserDTO) {
        userService.userRole(roleToUserDTO.getUserName()
                , roleToUserDTO.getRoleName());
    }

}
