package ru.netology.diplom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.netology.diplom.repository.AuthToken;
import ru.netology.diplom.repository.User;
import ru.netology.diplom.service.AuthService;

import javax.validation.constraints.NotBlank;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/")
public class AuthController {

    final private AuthService service;

    public AuthController(@Autowired AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody @Validated User user) {
        return service.login(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@CookieValue(value = "auth-token") @NotBlank String authToken) {
        return service.logout(authToken);
    }
}
