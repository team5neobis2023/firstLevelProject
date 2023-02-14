package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.UserEntity;
import com.neobis.g4g.girls_for_girls.exception.UserNotFoundException;
import com.neobis.g4g.girls_for_girls.repository.EmailService;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/password")
public class PasswordController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordController(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    // Process form submission from forgotPassword page
    @PostMapping("/forgot")
    public ResponseEntity<String> processForgotPasswordForm(@RequestParam("email") String userEmail, HttpServletRequest request) {

        // Lookup user in database by e-mail
        Optional<UserEntity> optional = userRepository.findByEmail(userEmail);

        if (!optional.isPresent()) {
            throw new UserNotFoundException(
                    MessageFormat.format("User {0} not found", userEmail));
        } else {

            // Generate random 36-character string token for reset password
            UserEntity user = optional.get();
            user.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            userRepository.save(user);

            String appUrl = request.getScheme() + "://" + request.getServerName() +":" + request.getLocalPort() + "/api/v1/password";

            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("g4g@gmail.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset?token=" + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);

        }

        return ResponseEntity.ok().body("A password reset link has been sent to " + userEmail);
    }

    // Display form to reset password
    @GetMapping("/reset")
    public ResponseEntity<String> displayResetPasswordPage(@RequestParam("token") String token) {

        Optional<UserEntity> user = userRepository.findByResetToken(token);

        if (user.isPresent()) { // Token found in DB
            return ResponseEntity.ok().body("Token is valid!");
        } else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");

    }

    @PostMapping("/reset")
    public ResponseEntity<String> setNewPassword(@RequestParam("token") String token, @RequestBody UserDTO password) {

        // Find the user associated with the reset token
        Optional<UserEntity> user = userRepository.findByResetToken(token);

        // This should always be non-null but we check just in case
        if (user.isPresent()) {

            UserEntity resetUser = user.get();

            // Set new password
            resetUser.setPassword(passwordEncoder.encode(password.getPassword()));

            // Set the reset token to null so it cannot be used again
            resetUser.setResetToken(null);

            // Save user
            userRepository.save(resetUser);

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            return ResponseEntity.ok().body("Password changed successfully");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed!");
        }
    }
}
