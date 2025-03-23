package com.kirigwi.springarchive.authorisation.userManagement.service;

import com.kirigwi.springarchive.authorisation.userManagement.dto.request.SignInRequestDto;
import com.kirigwi.springarchive.authorisation.userManagement.dto.request.UserRegistrationRequestDto;
import com.kirigwi.springarchive.authorisation.userManagement.dto.response.UserLoginResponseDto;
import com.kirigwi.springarchive.authorisation.userManagement.entity.Users;
import com.kirigwi.springarchive.authorisation.userManagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Transactional
    public void registerUser(UserRegistrationRequestDto request) {
        // Check if the username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        // Check if the email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoleid(1L);
        userRepository.insertUser(user);
    }

    public UserLoginResponseDto login(SignInRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found for username: {}", request.getUsername());
                    return new IllegalArgumentException("User not found");
                });

        String role = user.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("USER");

        String token = jwtService.generateToken(user.getUsername(), role);
        log.info("Generated Token: {}", token);

        return new UserLoginResponseDto(user.getId(), user.getEmail(),
                user.getUsername(), token);
    }
}
