package com.serverapp.serverapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.serverapp.serverapp.entity.Employee;
import com.serverapp.serverapp.entity.User;
import com.serverapp.serverapp.model.request.UserRequest;
import com.serverapp.serverapp.model.response.UserResponse;
import com.serverapp.serverapp.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Get all
    public List<UserResponse> fetchAllUser() {
        return userRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get by ID
    public UserResponse getUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));
    }

    // Create
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        user.setPassword(userRequest.getPassword());
        user.setUsername(userRequest.getUsername());

        Employee employee = new Employee();
        employee.setId(userRequest.getEmployeeId());
        user.setEmployee(employee);

        return convertToResponse(userRepository.save(user));
    }

    // Update
    public UserResponse updateUser(Integer id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));

        user.setPassword(userRequest.getPassword());
        user.setUsername(userRequest.getUsername());

        // Update employee object
        Employee employee = user.getEmployee(); // Ambil employee yang ada
        if (employee != null) {
            employee.setId(userRequest.getEmployeeId()); // Update ID jika diperlukan
        }

        return convertToResponse(userRepository.save(user));
    }

    // Delete
    public UserResponse deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));

        userRepository.delete(user);
        return convertToResponse(user); // Mengembalikan pengguna yang dihapus
    }

    // method to convert User to UserResponse
    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setPassword(user.getPassword());
        response.setUsername(user.getUsername());
        response.setEmployeeId(user.getEmployee() != null ? user.getEmployee().getId() : null); // Ambil employeeId dari
                                                                                                // objek Employee
        return response;
    }
}