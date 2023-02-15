package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.UserEntity;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import com.neobis.g4g.girls_for_girls.service.UserManager;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserManager userManager;

    @Autowired
    public UserController(UserRepository userRepository, UserManager userManager) {
        this.userRepository = userRepository;
        this.userManager = userManager;
    }

    @Operation(summary = "Получить аккаунт по его ID", tags = "Аккаунт")
    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @Operation(summary = "Получить все аккаунты и поиск по логину", tags = "Аккаунт")
    @GetMapping()
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Operation(summary = "Добавить новый аккаунт", tags = "Аккаунт")
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO, @AuthenticationPrincipal UserEntity authUser) {
        return saveUser(userDTO, authUser);
    }

    @Operation(summary = "Изменить аккаунт", tags = "Аккаунт")
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        return userManager.updateUser(userDTO);
    }
}
