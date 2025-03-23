package com.kirigwi.springarchive.authorisation.userManagement.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Users Entity for MyBatis Mapping
 */
@Schema(name = "Users", description = "User entity representing a system user")
public class Users implements UserDetails, Serializable {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Size(max = 100, message = "Username length cannot exceed 100 characters")
    @Length(max = 100, message = "Username length cannot exceed 100 characters")
    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 255, message = "Password length must be between 8 and 255 characters")
    @Schema(description = "User's password (hashed in DB)", example = "$2a$10$hashed_password")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 255, message = "Email length cannot exceed 255 characters")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address", example = "john@example.com")
    private String email;

    @NotNull(message = "Role ID cannot be empty")
    @Schema(description = "Role ID assigned to the user", example = "2")
    private Long roleid;

    @Schema(description = "Date of account creation", example = "2025-03-22T10:15:30")
    private Date createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setUsername(String username) { this.username = username; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = switch (roleid.intValue()) {
            case 2 -> "ADMIN";
            case 3 -> "LIBRARIAN";
            default -> "USER";
        };
        return List.of(new SimpleGrantedAuthority(role));
    }


    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getRoleid() { return roleid; }
    public void setRoleid(Long roleid) { this.roleid = roleid; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
