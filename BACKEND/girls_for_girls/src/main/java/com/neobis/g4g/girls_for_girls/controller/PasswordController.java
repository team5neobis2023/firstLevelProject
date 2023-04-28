package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/password")
public class PasswordController {

    private final PasswordService passwordService;

    @Autowired
    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    // Process form submission from forgotPassword page
    @PostMapping("/forgot/{email}")
    public ResponseEntity<String> processForgotPasswordForm(@PathVariable("email") String userEmail, HttpServletRequest request) {
        return passwordService.processForgotPasswordForm(userEmail, request);
    }

    // Display form to reset password
    @GetMapping("/reset/{token}")
    public ResponseEntity<String> displayResetPasswordPage(@PathVariable("token") String token) {
        return passwordService.displayResetPasswordPage(token);
    }

    @PostMapping("/reset/{token}")
    public ResponseEntity<String> setNewPassword(@PathVariable("token") String token, @RequestBody UserDTO password) {
        return passwordService.setNewPassword(token, password);
    }
}
