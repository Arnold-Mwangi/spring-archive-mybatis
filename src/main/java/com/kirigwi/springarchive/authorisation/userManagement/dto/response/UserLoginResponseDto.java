package com.kirigwi.springarchive.authorisation.userManagement.dto.response;

import lombok.Data;

@Data
public class UserLoginResponseDto {
    private Long id;
    private String email;
    private String username;
    private String bearer;

    public UserLoginResponseDto(Long id, String email,
                                String username, String bearer) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.bearer = bearer;
    }
}
