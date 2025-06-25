package com.kirigwi.springarchive.library.services;

import com.kirigwi.springarchive.library.entities.Book;
import com.kirigwi.springarchive.library.entities.BookCategory;
import com.kirigwi.springarchive.library.repositories.BookCategoryRepository;
import com.kirigwi.springarchive.library.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> getAllBooks(int page, int size) {
        int offset = (page - 1) * size;
        return bookRepository.getAllBooks(size, offset);
    }

    @Transactional
    public void addBook(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        bookRepository.insertBook(book);
        Book newBook = bookRepository.getBookByIsbn(book.getIsbn());
        log.error("New added book is: {}", newBook.toString());
    }
}
