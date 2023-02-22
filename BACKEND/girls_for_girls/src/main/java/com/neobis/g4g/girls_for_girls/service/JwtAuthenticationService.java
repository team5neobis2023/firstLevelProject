package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.LoginDTO;
import com.neobis.g4g.girls_for_girls.data.dto.TokenDTO;
import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.RefreshTokenEntity;
import com.neobis.g4g.girls_for_girls.data.entity.UserEntity;
import com.neobis.g4g.girls_for_girls.exception.UsernameAlreadyExistException;
import com.neobis.g4g.girls_for_girls.repository.EmailService;
import com.neobis.g4g.girls_for_girls.repository.RefreshTokenRepository;
import com.neobis.g4g.girls_for_girls.repository.UserGroupRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import com.neobis.g4g.girls_for_girls.security.TokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class JwtAuthenticationService {
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserDetailsManager userDetailsManager;
    private final EmailService emailService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final TokenGenerator tokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    public JwtAuthenticationService(UserRepository userRepository, UserGroupRepository userGroupRepository,
                                    UserDetailsManager userDetailsManager, EmailService emailService,
                                    DaoAuthenticationProvider daoAuthenticationProvider, TokenGenerator tokenGenerator,
                                    RefreshTokenRepository refreshTokenRepository,
                                    @Qualifier("jwtRefreshTokenAuthProvider") JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.userDetailsManager = userDetailsManager;
        this.emailService = emailService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.tokenGenerator = tokenGenerator;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    public ResponseEntity<?> register(UserDTO userDTO, HttpServletRequest request) {
        UserEntity user = new UserEntity();
        Random random = new Random();
        int token = random.nextInt(9999);
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userGroupRepository.findById(3));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPlaceOfBirth(userDTO.getPlaceOfBirth());
        user.setFile(userDTO.getFile());
        user.setResetToken(String.valueOf(token));


        try {
            userDetailsManager.createUser(user);
        } catch (UsernameAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        // Email message
        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setFrom("g4g@gmail.com");
        passwordResetEmail.setTo(user.getEmail());
        passwordResetEmail.setSubject("Account activation");
        passwordResetEmail.setText("Your activation code: " + user.getResetToken());

        emailService.sendEmail(passwordResetEmail);
        log.info("Success send email to " + userDTO.getEmail());


        return ResponseEntity.ok("User successfully registered");
    }

    public ResponseEntity<?> login(LoginDTO loginDTO) {
        try {
            Authentication authentication = daoAuthenticationProvider
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

            TokenDTO tokenDTO = tokenGenerator.createToken(authentication);

            RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
            Optional<UserEntity> user = userRepository.findByEmail(loginDTO.getEmail());

            refreshTokenEntity.setUserId(user.get().getId());
            refreshTokenEntity.setToken(tokenDTO.getRefreshToken());
            refreshTokenRepository.save(refreshTokenEntity);

            return ResponseEntity.ok(tokenDTO);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("incorrect username or password");
        }
    }

    public ResponseEntity<?> refreshToken(TokenDTO tokenDTO) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(tokenDTO.getRefreshToken());

        if (refreshTokenEntity == null) {
            return ResponseEntity.badRequest().body("This refresh was invalidated");
        }

        refreshTokenRepository.delete(refreshTokenEntity);

        Authentication authentication = jwtAuthenticationProvider
                .authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    public ResponseEntity<String> activateAccount(String token) {

        // Find the user associated with the reset token
        Optional<UserEntity> user = userRepository.findByResetToken(token);

        // This should always be non-null but we check just in case
        if (user.isPresent()) {

            UserEntity resetUser = user.get();

            // Set account enabled
            resetUser.setEnabled(true);

            // Set the reset token to null so it cannot be used again
            resetUser.setResetToken(null);

            // Save user
            userRepository.save(resetUser);

            return ResponseEntity.ok().body("Account activated successfully");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed!");
        }
    }
}
