package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.UserEntity;
import com.neobis.g4g.girls_for_girls.data.entity.UserGroupEntity;
import com.neobis.g4g.girls_for_girls.repository.UserGroupRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import com.neobis.g4g.girls_for_girls.service.UserManager;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;
    private final UserManager userManager;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserGroupRepository userGroupRepository, UserManager userManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userGroupRepository = userGroupRepository;
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
        try {

            UserGroupEntity userGroupEntity = userGroupRepository.findById(userDTO.getRole().getId());
            UserEntity newUser = new UserEntity();

            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            newUser.setRole(userGroupEntity);
            newUser.setFirstName(userDTO.getFirstName());
            newUser.setLastName(userDTO.getLastName());
            newUser.setPhoneNumber(userDTO.getPhoneNumber());
            newUser.setFile(userDTO.getFile());
            newUser.setPlaceOfBirth(userDTO.getPlaceOfBirth());
            userRepository.save(newUser);

            return ResponseEntity.ok().body(newUser.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @Operation(summary = "Изменить аккаунт", tags = "Аккаунт")
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        try {
            boolean exist = userRepository.existsById(userDTO.getId());

            if(exist) {
                UserEntity user = userRepository.findById(userDTO.getId());

                user.setEmail(userDTO.getEmail());
                user.setFirstName(userDTO.getFirstName());
                user.setRole(userDTO.getRole());
                user.setLastName(userDTO.getLastName());
                user.setPhoneNumber(userDTO.getPhoneNumber());
                user.setFile(userDTO.getFile());
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                user.setPlaceOfBirth(userDTO.getPlaceOfBirth());
                userRepository.save(user);

                return ResponseEntity.ok().body(user.getId());
            }
            throw new IllegalStateException("User with id " + userDTO.getId() + " does not exist");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }


}
