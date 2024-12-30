package com.serverapp.serverapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serverapp.serverapp.entity.Country;
import com.serverapp.serverapp.entity.Employee;
import com.serverapp.serverapp.model.request.CountryRequest;
import com.serverapp.serverapp.model.request.EmployeeRequest;
import com.serverapp.serverapp.service.EmployeeService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    // get all
    @GetMapping
    public List<Employee> getAllEmployee() {
        return employeeService.fetchAllEmployees();
    }

    // get by id
    @GetMapping("{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.findEmployeeById(id);
    }

    // create using manual dto
    @PostMapping("/create")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(employeeRequest);
    }

    // update
    @PutMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.updateEmployee(id, employeeRequest);
    }

    // delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build(); // Mengembalikan status 204 No Content
    }
}
