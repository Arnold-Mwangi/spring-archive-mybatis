package com.kirigwi.springarchive.authorisation.userManagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequestDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(max = 100, message = "Username length cannot exceed 100 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 255, message = "Password length must be between 8 and 255 characters")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 255, message = "Email length cannot exceed 255 characters")
    @Email(message = "Invalid email format")
    private String email;
}
