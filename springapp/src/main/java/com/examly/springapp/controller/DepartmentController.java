package com.examly.springapp.controller;

import com.examly.springapp.model.Department;
import com.examly.springapp.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public Department add(@RequestBody Department dept) { return departmentService.addDepartment(dept); }

    @GetMapping
    public List<Department> getAll() { return departmentService.getAllDepartments(); }

    @GetMapping("/{id}")
    public Optional<Department> getById(@PathVariable Long id) { return departmentService.getDepartmentById(id); }

    @PutMapping("/{id}")
    public Department update(@PathVariable Long id, @RequestBody Department dept) { return departmentService.updateDepartment(id, dept); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { departmentService.deleteDepartment(id); }
}
