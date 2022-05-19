package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mirea.webtice.backend.dto.response.MessageResponse;
import ru.mirea.webtice.backend.entity.Test;
import ru.mirea.webtice.backend.entity.User;
import ru.mirea.webtice.backend.repository.TestRepository;
import ru.mirea.webtice.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TestRepository testRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return user;
    }

    @GetMapping("/tests/{user_id}")
    public Set<Test> getUserTasks(@PathVariable("user_id") Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        return user.get().getTests();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("User with id " + id + " deleted successfully"));
    }
}
