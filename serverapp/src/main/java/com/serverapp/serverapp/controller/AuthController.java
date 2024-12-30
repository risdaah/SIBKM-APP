package com.serverapp.serverapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.serverapp.serverapp.model.request.LoginRequest;
//import com.serverapp.serverapp.model.request.LoginRequest;
import com.serverapp.serverapp.model.request.RegisterRequest;
import com.serverapp.serverapp.model.response.LoginResponse;
import com.serverapp.serverapp.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import com.serverapp.serverapp.entity.Employee;
//import com.serverapp.serverapp.entity.User;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Employee> register(@RequestBody RegisterRequest registerRequest) {
        Employee registeredEmployee = authService.register(registerRequest);
        return new ResponseEntity<>(registeredEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        LoginResponse response = authService.login(loginRequest, request);
        return ResponseEntity.ok(response);
    }

    // add role to user
    @PostMapping("/{userId}/role/{roleName}")
    public ResponseEntity<String> addRoleToUser(@PathVariable Integer userId, @PathVariable String roleName) {
        try {
            authService.addRoleToUser(userId, roleName);
            return ResponseEntity.ok("Role " + roleName + " berhasil ditambahkan ke user dengan ID " + userId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}