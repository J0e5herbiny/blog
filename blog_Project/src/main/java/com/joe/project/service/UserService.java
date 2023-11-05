package com.joe.project.service;

import com.joe.project.dto.RoleDto;
import com.joe.project.dto.UserDto;
import com.joe.project.dto.UserRoleDto;
import com.joe.project.entity.Role;
import com.joe.project.entity.User;
import com.joe.project.repository.RoleRepository;
import com.joe.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userByName = userRepository.findUserByName(username);
        if (userByName == null){
            throw new UsernameNotFoundException("User with name:"+userByName+" doesn't exist !");
        }
        Collection<SimpleGrantedAuthority> authorities =
                new ArrayList<>();
        userByName.getRoles().forEach(
                role -> {
                    authorities.add(new SimpleGrantedAuthority(role.roleDto().getRoleName()) );
                }
        );

                return new org.springframework.security.core.userdetails.User(
                        userByName.getName(),
                        userByName.getPassword(),
                        authorities);
    }

    public void createUser(UserDto userDto){
        Optional<User> userByEmail = userRepository.findUserByEmail(userDto.getEmail());
        if (userByEmail.isPresent()){
            throw new IllegalStateException("Email taken !");
        }
        User user = new User(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword()
        );
        userRepository.save(user).userDto();
    }

    public List<UserDto> readUsers(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for ( User user : users ) {
            userDtos.add(user.userDto());
        }
        return userDtos;
    }

    public List<UserRoleDto> readUsersRole() {
        List<User> users = userRepository.findAll();
        List<UserRoleDto> userDtos = new ArrayList<>();
        for (User user : users
        ) {
            UserRoleDto temp = new UserRoleDto(user.getId()
                    , user.getName()
                    , user.getPassword()
                    , user.getEmail()
                    , user.getRoles());
            userDtos.add(temp);
        }

        return userDtos;
    }

    public UserDto readUser(UUID id){
        return userRepository.findById(id).get().userDto();
    }

    public UserDto readUserByUserName(String username) {
        User user = userRepository.findUserByName(username);

        return user.userDto();
    }

    public void updateUser(UUID id,
                           UserDto userDto){
        User user = userRepository.findById(id).
                orElseThrow( ()->new IllegalStateException("Doesn't exist !") );
        if (user != null && !Objects.equals(user, userDto)){
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
        }
    }

    public void deleteUser(UUID id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new IllegalStateException("Doesn't exist !");
        }
        userRepository.deleteById(id);
    }

    public void createRole(RoleDto roleDto, String role_user){
        Role role = new Role(roleDto.getRoleName());
        roleRepository.save(role).roleDto();
    }

    public RoleDto saveRole(RoleDto roleDto) {
        Role role = Role.builder()
                .name(roleDto.getRoleName())
                .build();

        return roleRepository.save(role).roleDto();
    }

    public void userRole(String userName,
                         String roleName){
        User user = userRepository.findUserByName(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public UserDto follow(UUID userId,
                          UUID followerId){
        User user = userRepository.findById(userId).get();
        User follower = userRepository.findById(followerId).get();
        user.followUser(follower);
        return userRepository.save(user).userDto();
    }

    public UserDto unfollow(UUID userId,
                            UUID followerId){
        User user = userRepository.findById(userId).get();
        User follower = userRepository.findById(followerId).get();
        user.unfollowUser(follower);
        return userRepository.save(user).userDto();
    }

}
