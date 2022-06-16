package com.task.test.dto;

import com.task.test.enums.ProfileRole;
import com.task.test.enums.ProfileStatus;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ProfileDTO {
    private Integer id;
    @NotBlank(message = "name is valid")
    private String name;
    @NotBlank(message = "surname is valid")
    private String surname;
    @Email(message = "email is valid")
    private String email;
    @NotNull(message = "status is valid")
    private ProfileStatus status;
    @NotNull(message = "password is valid. min length 8, max length 24")
    private String password;
    @NotNull(message = "contact is valid")
    private String contact;
    @NotNull(message = "role is valid")
    private ProfileRole role;
    private LocalDateTime createdDate;
}