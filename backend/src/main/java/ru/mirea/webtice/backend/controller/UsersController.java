package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mirea.webtice.backend.dto.response.MessageResponse;
import ru.mirea.webtice.backend.entity.User;
import ru.mirea.webtice.backend.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("User with id " + id + " deleted successfully"));
    }
}
