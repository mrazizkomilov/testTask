package com.task.test.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AuthorizationDTO {
    @Email(message = "Please provide a email")
    private String email;
    @NotBlank(message = "Please provide a  password")
    @Size(min = 5, max = 15, message = "password min 5 max 15 ta bolishi kerak")
    private String password;
}
