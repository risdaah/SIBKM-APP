package com.serverapp.serverapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.Authentication;
import com.serverapp.serverapp.entity.Employee;
import com.serverapp.serverapp.entity.Role;
import com.serverapp.serverapp.entity.User;
import com.serverapp.serverapp.model.request.LoginRequest;
import com.serverapp.serverapp.model.request.RegisterRequest;
import com.serverapp.serverapp.model.response.LoginResponse;
import com.serverapp.serverapp.repository.EmployeeRepository;
import com.serverapp.serverapp.repository.RoleRepository;
import com.serverapp.serverapp.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private final AppUserDetailService appUserDetailService;
    private RoleRepository roleRepository;

    // register versi bean utils dengan default role USER
    public Employee register(RegisterRequest registerRequest) {
        // Pengecekan apakah email sudah terdaftar
        if (employeeRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar.");
        }

        Employee employee = new Employee();
        User user = new User();

        try {
            BeanUtils.copyProperties(employee, registerRequest);
            BeanUtils.copyProperties(user, registerRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error saat menyimpan properti", e);
        }

        // Set password yang terenkripsi
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Set role default
        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER tidak ditemukan."));
        user.getRoles().add(defaultRole); // Menambahkan role default ke user

        user.setEmployee(employee);
        employee.setUser(user);

        return employeeRepository.save(employee);
    }

    // tambah role baru
    public void addRoleToUser(Integer userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan: " + userId));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + roleName + " tidak ditemukan."));

        // Tambahkan role baru ke daftar roles
        user.getRoles().add(role);
        userRepository.save(user); // Simpan perubahan
    }

    public class CustomException extends RuntimeException {
        private HttpStatus httpStatus;

        public CustomException(String message, HttpStatus httpStatus) {
            super(message);
            this.httpStatus = httpStatus;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }

    // customize login with session
    public LoginResponse login(LoginRequest loginRequest, HttpServletRequest request) {
        try {
            // Autentikasi
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword());

            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Mendapatkan detail pengguna
            UserDetails userDetails = appUserDetailService.loadUserByUsername(loginRequest.getUsername());

            // Mendapatkan email untuk respons login
            User user = userRepository
                    .findByUsernameOrEmployeeEmail(loginRequest.getUsername(), loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Mendapatkan otoritas untuk respons login
            List<String> authorities = userDetails
                    .getAuthorities()
                    .stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toList());

            // Menyiapkan respons
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUsername(userDetails.getUsername());
            loginResponse.setEmail(user.getEmployee().getEmail());
            loginResponse.setAuthorities(authorities);
            loginResponse.setSessionId(request.getSession().getId());

            return loginResponse;
        } catch (BadCredentialsException e) {
            throw new CustomException("Bad Credential: Wrong Username or Password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new CustomException("Something Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // customize login
    // public LoginResponse login(LoginRequest loginRequest) {
    // // Autentikasi
    // UsernamePasswordAuthenticationToken authReq = new
    // UsernamePasswordAuthenticationToken(
    // loginRequest.getUsername(),
    // loginRequest.getPassword());

    // Authentication auth = authenticationManager.authenticate(authReq);
    // SecurityContextHolder.getContext().setAuthentication(auth);

    // // Mendapatkan detail pengguna
    // UserDetails userDetails =
    // appUserDetailService.loadUserByUsername(loginRequest.getUsername());

    // // Mendapatkan email untuk respons login
    // User user = userRepository
    // .findByUsernameOrEmployeeEmail(loginRequest.getUsername(),
    // loginRequest.getUsername())
    // .orElseThrow(() -> new RuntimeException("User not found"));

    // // Mendapatkan otoritas untuk respons login
    // List<String> authorities = userDetails
    // .getAuthorities()
    // .stream()
    // .map(authority -> authority.getAuthority())
    // .collect(Collectors.toList());

    // // Menyiapkan respons
    // LoginResponse loginResponse = new LoginResponse();
    // loginResponse.setUsername(userDetails.getUsername());
    // loginResponse.setEmail(user.getEmployee().getEmail()); // Memastikan model
    // User memiliki relasi ke Employee
    // loginResponse.setAuthorities(authorities);

    // return loginResponse;
    // }

    // register versi bean utils tanpa default role
    // public Employee register(RegisterRequest registerRequest) {
    // // Pengecekan apakah email sudah terdaftar
    // if (employeeRepository.existsByEmail(registerRequest.getEmail())) {
    // throw new RuntimeException("Email sudah terdaftar.");
    // }

    // Employee employee = new Employee();
    // User user = new User();

    // try {
    // BeanUtils.copyProperties(employee, registerRequest);
    // BeanUtils.copyProperties(user, registerRequest);
    // } catch (Exception e) {
    // throw new RuntimeException("Error saat menyimpan properti", e);
    // }

    // // Set password yang terenkripsi
    // user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

    // user.setEmployee(employee);
    // employee.setUser(user);

    // return employeeRepository.save(employee);
    // }

    // private final AuthenticationManager authenticationManager;
    // private final DataUserService dataUserService;

    // register versi manual
    // public Employee register(RegisterRequest registerRequest) {
    // // Cek apakah Employee sudah ada berdasarkan email
    // if (employeeRepository.existsByEmail(registerRequest.getEmail())) {
    // throw new RuntimeException("Employee dengan email ini sudah terdaftar.");
    // }

    // // Buat data Employee baru
    // Employee employee = new Employee();
    // employee.setName(registerRequest.getName());
    // employee.setEmail(registerRequest.getEmail());
    // employee.setPhone(registerRequest.getPhone());

    // // Buat data User baru
    // User user = new User();
    // user.setUsername(registerRequest.getUsername());
    // user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); //
    // Enkripsi password

    // // Mengaitkan User dengan Employee
    // user.setEmployee(employee);
    // employee.setUser(user); // Ini mengatur relasi dua arah

    // // Simpan Employee (yang juga menyimpan User)
    // employeeRepository.save(employee); // Dengan cascade, User juga akan disimpan

    // return employee;
    // }

}