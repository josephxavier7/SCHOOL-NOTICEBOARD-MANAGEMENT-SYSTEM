package com.examly.springapp.service;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    public Optional<User> getByUsername(String username) { return userRepository.findByUsername(username); }

    public User updateProfile(String username, User updated) {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setEmail(updated.getEmail());
        user.setDepartment(updated.getDepartment());
        user.setGradeLevel(updated.getGradeLevel());
        return userRepository.save(user);
    }

    public Page<User> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public User toggleUserActive(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }
}
