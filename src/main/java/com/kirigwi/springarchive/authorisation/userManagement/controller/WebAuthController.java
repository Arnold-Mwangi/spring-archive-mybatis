package com.kirigwi.springarchive.authorisation.userManagement.controller;

import com.kirigwi.springarchive.authorisation.userManagement.dto.request.UserRegistrationRequestDto;
import com.kirigwi.springarchive.authorisation.userManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WebAuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Loads templates/login.html
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Add register.html to support this
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute UserRegistrationRequestDto request, Model model) {
        try {
            userService.registerUser(request);
            model.addAttribute("success", "Registration successful! You can now log in.");
            return "login";  // redirect to login after successful registration
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";  // show form again with error
        }
    }
}
