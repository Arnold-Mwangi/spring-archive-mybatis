package com.kirigwi.springarchive.library.services;

import com.kirigwi.springarchive.library.entities.Book;
import com.kirigwi.springarchive.library.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> getAllBooks(int page, int size) {
        int offset = (page - 1) * size;
        return bookRepository.getAllBooks(size, offset);
    }
}
