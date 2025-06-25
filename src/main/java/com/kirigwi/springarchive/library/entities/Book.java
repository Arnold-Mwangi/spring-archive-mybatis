package com.kirigwi.springarchive.library.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Book Entity for MyBatis Mapping
 */
@Schema(name = "Book", description = "Book entity representing books in the catalog")
public class Book implements Serializable {

    @Schema(description = "Book ID", example = "1")
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 255, message = "Title length cannot exceed 255 characters")
    @Schema(description = "Book title", example = "The Catcher in the Rye")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    @Size(max = 255, message = "Author length cannot exceed 255 characters")
    @Schema(description = "Book author", example = "J.D. Salinger")
    private String author;

    @NotBlank(message = "ISBN cannot be empty")
    @Size(max = 20, message = "ISBN length cannot exceed 20 characters")
    @Schema(description = "Book ISBN number", example = "978-0-316-76948-0")
    private String isbn;

    @Schema(description = "Publication date", example = "1951-07-16")
    private LocalDateTime publishedDate;

    @Schema(description = "Book cover image URL", example = "https://example.com/cover.jpg")
    @Size(max = 500, message = "Thumbnail URL length cannot exceed 500 characters")
    private String thumbnailUrl;

    @Schema(description = "Language of the book", example = "English")
    @Size(max = 20, message = "Language length cannot exceed 20 characters")
    private String language;

    @NotNull(message = "Category cannot be null")
    @Schema(description = "Category of the book")
    private BookCategory category;


    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }
    @Schema(description = "Date the book was added to the system", example = "2025-03-22T10:15:30")
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public LocalDateTime getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDateTime publishedDate) { this.publishedDate = publishedDate; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }


    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
