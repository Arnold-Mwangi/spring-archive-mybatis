package com.kirigwi.springarchive.library.controllers;

import com.kirigwi.springarchive.library.entities.Book;
import com.kirigwi.springarchive.library.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            log.error("Authentication failed or is not present.");
            return "redirect:/login";  // Redirect to login page if not authenticated
        }

        String role = auth.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        log.error("The user role is: {}", role);

        List<Book> books = bookService.getAllBooks(page, size);

        model.addAttribute("books", books);
        model.addAttribute("userRole", role);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "home";  // Return home.html view
    }

}
