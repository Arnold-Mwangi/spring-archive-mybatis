package com.kirigwi.springarchive.library.services;

import com.kirigwi.springarchive.authorisation.userManagement.dto.response.PaginatedResponse;
import com.kirigwi.springarchive.library.entities.BookCategory;
import com.kirigwi.springarchive.library.repositories.BookCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;

    public PaginatedResponse<BookCategory> getBookCategories(int page, int size) {
        int offset = (page) * size;
        List<BookCategory> categories = bookCategoryRepository.getCategoriesWithPagination(offset, size);
        int totalItems = bookCategoryRepository.getTotalCategories();
        return new PaginatedResponse<>(categories, page, size, totalItems);
    }

    public BookCategory getCategoryById(Long categoryId) {
        return bookCategoryRepository.getCategoryById(categoryId);
    }
}
