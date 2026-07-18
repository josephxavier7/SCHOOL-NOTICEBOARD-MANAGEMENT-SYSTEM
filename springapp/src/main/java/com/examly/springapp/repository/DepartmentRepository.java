package com.examly.springapp.repository;

import com.examly.springapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByIsActiveTrue();
}
