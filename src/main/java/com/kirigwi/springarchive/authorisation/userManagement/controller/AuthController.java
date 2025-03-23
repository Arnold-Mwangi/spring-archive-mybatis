package com.kirigwi.springarchive.authorisation.userManagement.controller;


import com.kirigwi.springarchive.authorisation.userManagement.dto.request.SignInRequestDto;
import com.kirigwi.springarchive.authorisation.userManagement.dto.request.UserRegistrationRequestDto;
import com.kirigwi.springarchive.authorisation.userManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequestDto request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody SignInRequestDto request) {
        try {
            return ResponseEntity.ok(userService.login(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
}