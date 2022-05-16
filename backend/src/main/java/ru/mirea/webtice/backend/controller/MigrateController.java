package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.webtice.backend.dto.response.MessageResponse;
import ru.mirea.webtice.backend.entity.Role;
import ru.mirea.webtice.backend.entity.User;
import ru.mirea.webtice.backend.enums.RoleEnum;
import ru.mirea.webtice.backend.repository.RoleRepository;
import ru.mirea.webtice.backend.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/migrate")
public class MigrateController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("")
    public ResponseEntity<?> initDataBase() {
        Role roleAdmin = new Role(RoleEnum.ROLE_ADMIN);
        Role roleUser = new Role(RoleEnum.ROLE_USER);
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        User userAdmin = new User("admin", "admin@webtice.ru", encoder.encode("admin"));
        User userUser = new User("student", "student@webtice.ru", encoder.encode("user"));
        Set<Role> roles = new HashSet<>();
        Set<Role> roles1 = new HashSet<>();
        roles.add(roleAdmin);
        roles1.add(roleUser);
        userAdmin.setRoles(roles);
        userUser.setRoles(roles1);
        userRepository.save(userAdmin);
        userRepository.save(userUser);
        return ResponseEntity.ok(new MessageResponse("Migrations created successfully!"));
    }
}
