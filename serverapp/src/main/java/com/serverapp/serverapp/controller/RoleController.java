package com.serverapp.serverapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.serverapp.serverapp.entity.Role;
import com.serverapp.serverapp.service.RoleService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    // Get all regions
    @GetMapping
    public List<Role> fetchAllRoles() {
        return roleService.fetchAllRole();
    }

    // Get region by ID
    @GetMapping("{id}")
    public Role findRoleById(@PathVariable Integer id) {
        return roleService.findRoleById(id);
    }

    // Create
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

}
