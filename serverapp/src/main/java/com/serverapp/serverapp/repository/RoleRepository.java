package com.serverapp.serverapp.repository;

import com.serverapp.serverapp.entity.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Optional<Role> findByRole(String name);

    Optional<Role> findByName(String name);
}
