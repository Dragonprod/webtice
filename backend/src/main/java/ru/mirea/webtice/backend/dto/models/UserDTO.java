package ru.mirea.webtice.backend.dto.models;

import ru.mirea.webtice.backend.entity.Role;
import ru.mirea.webtice.backend.entity.User;

import java.util.List;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;

    public UserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
