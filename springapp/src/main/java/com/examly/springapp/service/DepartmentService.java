package com.examly.springapp.service;

import com.examly.springapp.model.Department;
import com.examly.springapp.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired private DepartmentRepository departmentRepository;

    public Department addDepartment(Department dept) { return departmentRepository.save(dept); }
    public List<Department> getAllDepartments() { return departmentRepository.findByIsActiveTrue(); }
    public Optional<Department> getDepartmentById(Long id) { return departmentRepository.findById(id); }
    public Department updateDepartment(Long id, Department dept) {
        dept.setId(id);
        return departmentRepository.save(dept);
    }
    public void deleteDepartment(Long id) {
        departmentRepository.findById(id).ifPresent(d -> { d.setActive(false); departmentRepository.save(d); });
    }
}
