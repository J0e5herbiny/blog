package com.joe.project.repository;

import com.joe.project.entity.User;
import com.joe.project.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Override
    Optional<User> findById(UUID uuid);

    default  User getById(UUID uuid){
        return findById(uuid).orElseThrow(RecordNotFoundException::new);
    }
    Optional<User> findUserByEmail(String email);

    User findUserByName(String name);

}
