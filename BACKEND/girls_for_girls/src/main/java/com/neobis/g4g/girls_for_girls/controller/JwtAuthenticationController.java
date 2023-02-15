package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.LoginDTO;
import com.neobis.g4g.girls_for_girls.data.dto.TokenDTO;
import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.service.JwtAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@Tag(name = "Authentication service", description = "Регистрация, логин и рефреш токена")
@RequestMapping("/api/v1/auth")
public class JwtAuthenticationController {
    private final JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    public JwtAuthenticationController(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        return jwtAuthenticationService.register(userDTO, request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return jwtAuthenticationService.login(loginDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenDTO tokenDTO) {
        return jwtAuthenticationService.refreshToken(tokenDTO);
    }

    @GetMapping("/active")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String token) {
        return jwtAuthenticationService.activateAccount(token);
    }
}
