package com.kirigwi.springarchive.library.repositories;

import com.kirigwi.springarchive.library.entities.Book;
import com.kirigwi.springarchive.library.entities.BookCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Ensures MyBatis and Spring are properly loaded
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @BeforeEach
    void setup() {
        // Insert a category first
        BookCategory category = new BookCategory();
        category.setName("Programming");

        bookCategoryRepository.safeInsertCategory(category);
        BookCategory category2 =
                bookCategoryRepository.findCategoryByName(category.getName());

        // Insert a book linked to the category
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setCategory(category2);
        book.setPublishedDate(LocalDateTime.now());
        book.setCreatedAt(LocalDateTime.now());

        bookRepository.insertBook(book);  // Insert book before running tests
    }


    @Test
    public void testGetAllBooks() {
        List<Book> books = bookRepository.getAllBooks(10, 0);

        assertNotNull(books);
        assertFalse(books.isEmpty(), "Book list should not be empty");

        books.forEach(book -> {
            assertNotNull(book.getTitle(), "Book title should not be null");
            assertNotNull(book.getCategory(), "Category should not be null");
            assertNotNull(book.getCategory().getName(), "Category name should not be null");
            System.out.println(book.getTitle() + " - " + book.getCategory().getName());
        });
    }
}
