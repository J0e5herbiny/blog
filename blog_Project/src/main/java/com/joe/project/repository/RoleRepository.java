package com.joe.project.repository;

import com.joe.project.entity.Role;
import com.joe.project.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {


    Optional<Role> findById(UUID uuid);

    default Role getById(UUID uuid){
        return findById(uuid).orElseThrow(RecordNotFoundException::new);
    }
    Role findByName(String roleName);
}
