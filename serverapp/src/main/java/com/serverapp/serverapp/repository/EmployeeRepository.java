package com.serverapp.serverapp.repository;

import com.serverapp.serverapp.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmailOrPhone(String email, String phone);

    boolean existsByEmail(String email);
}
