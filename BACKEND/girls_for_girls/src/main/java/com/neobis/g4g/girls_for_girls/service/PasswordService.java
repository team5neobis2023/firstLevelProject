package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.UserNotFoundException;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class PasswordService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> processForgotPasswordForm(String userEmail, HttpServletRequest request) {

        // Lookup user in database by e-mail
        Optional<User> optional = userRepository.findByEmail(userEmail);
        Random random = new Random();
        if (!optional.isPresent()) {
            throw new UserNotFoundException(
                    MessageFormat.format("User {0} not found", userEmail));
        } else {

            // Generate random 36-character string token for reset password
            User user = optional.get();
            user.setResetToken(String.valueOf(random.nextInt(1000,9999)));

            // Save token to database
            userRepository.save(user);


            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("g4g@gmail.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To activate your account enter this code: " + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);
            log.info("Success send email to " + userEmail);

        }

        return ResponseEntity.ok().body("A password reset link has been sent to " + userEmail);
    }


    public ResponseEntity<String> displayResetPasswordPage(String token) {

        Optional<User> user = userRepository.findByResetToken(token);

        if (user.isPresent()) { // Token found in DB
            return ResponseEntity.ok().body("Token is valid!");
        } else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
    }

    public ResponseEntity<String> setNewPassword(String token, UserDTO password) {

        // Find the user associated with the reset token
        Optional<User> user = userRepository.findByResetToken(token);

        // This should always be non-null but we check just in case
        if (user.isPresent()) {

            User resetUser = user.get();

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
