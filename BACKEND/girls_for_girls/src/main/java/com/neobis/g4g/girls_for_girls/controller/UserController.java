package com.neobis.g4g.girls_for_girls.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neobis.g4g.girls_for_girls.data.dto.ChangePassDTO;
import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import com.neobis.g4g.girls_for_girls.service.UserManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userRepository.findById(id).get();
    }

    @Operation(summary = "Получить свою информацию", tags = "Аккаунт")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/myinfo")
    public User getMyInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(((UserDetails)principal).getUsername()).get();
    }

    @Operation(summary = "Получить все аккаунты и поиск по логину", tags = "Аккаунт")
    @SecurityRequirement(name = "JWT")
    @GetMapping()
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}/likedArticles")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить все лайкнутые пользователем посты", tags = "Аккаунт")
    public ResponseEntity<?> getAllArticlesByLikedUsersId(@PathVariable("id")  long id){
        return userManager.getAllArticlesByLikedUsersId(id);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}/notifications")
    @Operation(summary = "Получить все уведомления пользователя", tags = "Аккаунт")
    public ResponseEntity<?> getAllNotificationsByUserId(@PathVariable("id") long id){
        return userManager.getAllNotificationsByUserId(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping("/change-password")
    @Operation(summary = "Изменить пароль", tags = "Аккаунт")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePassDTO changePassDTO,
                                            BindingResult bindingResult,
                                            @AuthenticationPrincipal User user){
        return userManager.changePassword(changePassDTO, bindingResult, user);
    }

    @Operation(summary = "Добавить новый аккаунт", tags = "Аккаунт")
    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO, @AuthenticationPrincipal User authUser) {
        return saveUser(userDTO, authUser);
    }

    @Operation(summary = "Изменить аккаунт", tags = "Аккаунт")
    @SecurityRequirement(name = "JWT")
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        return userManager.updateUser(userDTO);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotUpdatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
