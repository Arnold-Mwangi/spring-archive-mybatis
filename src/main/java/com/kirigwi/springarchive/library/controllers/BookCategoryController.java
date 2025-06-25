package com.kirigwi.springarchive.library.controllers;

import com.kirigwi.springarchive.authorisation.userManagement.dto.response.PaginatedResponse;
import com.kirigwi.springarchive.library.entities.BookCategory;
import com.kirigwi.springarchive.library.services.BookCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("book/categories")
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;

    @GetMapping
    public PaginatedResponse<BookCategory> getAllBookCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookCategoryService.getBookCategories(page, size);
    }

}
