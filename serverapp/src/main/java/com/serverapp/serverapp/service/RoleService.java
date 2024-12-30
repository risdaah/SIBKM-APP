package com.serverapp.serverapp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.serverapp.serverapp.entity.Role;
import com.serverapp.serverapp.repository.RoleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepositoty;

    // Get all
    public List<Role> fetchAllRole() {
        return roleRepositoty.findAll();
    }

    // Find by ID
    public Role findRoleById(Integer id) {
        return roleRepositoty
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!"));
    }

    // Create
    public Role createRole(Role role) {
        return roleRepositoty.save(role);
    }
}
