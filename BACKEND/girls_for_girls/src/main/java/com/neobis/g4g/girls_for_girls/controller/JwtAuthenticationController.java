package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.LoginDTO;
import com.neobis.g4g.girls_for_girls.data.dto.TokenDTO;
import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.RefreshTokenEntity;
import com.neobis.g4g.girls_for_girls.data.entity.UserEntity;
import com.neobis.g4g.girls_for_girls.exception.UsernameAlreadyExistException;
import com.neobis.g4g.girls_for_girls.repository.RefreshTokenRepository;
import com.neobis.g4g.girls_for_girls.repository.UserGroupRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import com.neobis.g4g.girls_for_girls.security.TokenGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@Tag(name = "Authentication service", description = "Регистрация, логин и рефреш токена")
@RequestMapping("/api/v1/auth")
public class JwtAuthenticationController {

    private final TokenGenerator tokenGenerator;
    private final UserDetailsManager userDetailsManager;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtRefreshTokenAuthProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;

    @Autowired
    public JwtAuthenticationController(
            TokenGenerator tokenGenerator,
            UserDetailsManager userDetailsManager,
            DaoAuthenticationProvider daoAuthenticationProvider,
            @Qualifier("jwtRefreshTokenAuthProvider") JwtAuthenticationProvider jwtRefreshTokenAuthProvider,
            RefreshTokenRepository refreshTokenRepository,
            UserRepository userRepository, PasswordEncoder passwordEncoder, UserGroupRepository userGroupRepository) {
        this.tokenGenerator = tokenGenerator;
        this.userDetailsManager = userDetailsManager;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtRefreshTokenAuthProvider = jwtRefreshTokenAuthProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userGroupRepository = userGroupRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        UserEntity user = new UserEntity();

        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userGroupRepository.findById(3));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setFile(userDTO.getFile());


        try {
            userDetailsManager.createUser(user);
        } catch (UsernameAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, userDTO.getPassword(),
                Collections.emptyList());

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
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

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenDTO tokenDTO) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(tokenDTO.getRefreshToken());

        if (refreshTokenEntity == null) {
            return ResponseEntity.badRequest().body("This refresh was invalidated");
        }

        refreshTokenRepository.delete(refreshTokenEntity);

        Authentication authentication = jwtRefreshTokenAuthProvider
                .authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }
}
