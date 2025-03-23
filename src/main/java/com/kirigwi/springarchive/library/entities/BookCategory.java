package com.kirigwi.springarchive.library.entities;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Categories Entity for MyBatis Mapping
 */
@Schema(name = "BookCategory", description = "Entity representing book categories")
public class BookCategory implements Serializable {

    @Schema(description = "Category ID", example = "1")
    private Long id;

    @NotBlank(message = "Category name cannot be empty")
    @Size(max = 100, message = "Category name length cannot exceed 100 characters")
    @Schema(description = "Name of the book category", example = "Science Fiction")
    private String name;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
