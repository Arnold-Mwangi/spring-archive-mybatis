package com.kirigwi.springarchive.library.controllers;

import com.kirigwi.springarchive.library.entities.Book;
import com.kirigwi.springarchive.library.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PublicController {

    @GetMapping("/")
    public String landingPage() {
        return "index"; // Renders index.html
    }
}
