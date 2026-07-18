package com.examly.springapp.controller;

import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/profile")
    public User getProfile(Principal principal) {
        return userService.getByUsername(principal.getName()).orElseThrow();
    }

    @PutMapping("/api/users/profile")
    public User updateProfile(@RequestBody User user, Principal principal) {
        return userService.updateProfile(principal.getName(), user);
    }

    @GetMapping("/api/admin/users")
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return userService.getAllUsers(page, size);
    }

    @PostMapping("/api/admin/users/{id}/activate")
    public User toggleActive(@PathVariable Long id) {
        return userService.toggleUserActive(id);
    }
}
