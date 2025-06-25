package com.kirigwi.springarchive.library.controllers;

import com.kirigwi.springarchive.library.entities.Book;
import com.kirigwi.springarchive.library.entities.BookCategory;
import com.kirigwi.springarchive.library.services.BookCategoryService;
import com.kirigwi.springarchive.library.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/books")
@Slf4j
public class BookWebController {
    private final BookService bookService;
    private final BookCategoryService bookCategoryService;


    public BookWebController(BookService bookService, BookCategoryService bookCategoryService) {
        this.bookService = bookService;
        this.bookCategoryService = bookCategoryService;
    }

    @PostMapping("/add")
    public String addBook(@RequestParam("categoryId") Long categoryId,
                          @RequestParam("title") String title,
                          @RequestParam("author") String author,
                          @RequestParam("isbn") String isbn,
                          @RequestParam("publishedDate") String publishedDate,
                          @RequestParam("thumbnailUrl") String thumbnailUrl,
                          @RequestParam("language") String language,
                          RedirectAttributes redirectAttributes) {
        try {
            log.error("Adding book: {} - {} - {}", author, title, isbn);

            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setLanguage(language);
            book.setThumbnailUrl(thumbnailUrl);

            // Convert publishedDate
            LocalDateTime parsedDate = LocalDate.parse(publishedDate).atStartOfDay();
            book.setPublishedDate(parsedDate);

            // Get category
            BookCategory category = bookCategoryService.getCategoryById(categoryId);
            book.setCategory(category);

            bookService.addBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (Exception e) {
            log.error("Error while adding book", e);  // <--- this will print the full stack trace
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add book: " + e.getMessage());
        }

        return "redirect:/home";
    }


}
