package com.kirigwi.springarchive.authorisation.userManagement.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * Represents a user role in the system.
 */
@Getter
@Setter
@Schema(description = "Represents a user role in the system.")
public class Role implements Serializable {

    @Schema(description = "Unique identifier of the role.", example = "1")
    private Long id;

    @NotBlank(message = "Role name cannot be empty.")
    @Size(max = 50, message = "Role name cannot exceed 50 characters.")
    @Schema(description = "The name of the role.", example = "ADMIN")
    private String rolename;

    @Size(max = 225, message = "Description cannot exceed 225 characters.")
    @Schema(description = "A brief description of the role.", example = "Administrator role with full access.")
    private String description;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRolename() { return rolename; }
    public void setRolename(String rolename) { this.rolename = rolename; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}