package com.serverapp.serverapp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.serverapp.serverapp.entity.Country;
import com.serverapp.serverapp.entity.Employee;
import com.serverapp.serverapp.entity.Region;
import com.serverapp.serverapp.model.request.CountryRequest;
import com.serverapp.serverapp.model.request.EmployeeRequest;
import com.serverapp.serverapp.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    // get all
    public List<Employee> fetchAllEmployees() {
        return employeeRepository.findAll();
    }

    // get by id
    public Employee findEmployeeById(Integer id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found!"));
    }

    // create with manual DTO
    public Employee addEmployee(EmployeeRequest request) {

        Employee employee = new Employee();
        employee.setEmail(request.getEmail());
        employee.setName(request.getName());
        employee.setPhone(request.getPhone());

        return employeeRepository.save(employee);
    }

    // update
    public Employee updateEmployee(Integer id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setEmail(request.getEmail());
        employee.setName(request.getName());
        employee.setPhone(request.getPhone());

        return employeeRepository.save(employee);
    }

    // delete
    public void deleteEmployee(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    // function convertToResponse
    // private EmployeeResponse convertToResponse(Employee employee) {
    // EmployeeResponse response = new EmployeeResponse();
    // response.setId(employee.getId());
    // response.setEmail(employee.getEmail());
    // response.setName(employee.getName());
    // response.setPhone(employee.getPhone());
    // return response;
    // }
}
