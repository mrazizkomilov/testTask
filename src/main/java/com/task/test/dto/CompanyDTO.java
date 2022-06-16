package com.task.test.dto;

import com.task.test.enums.ProfileRole;
import com.task.test.enums.ProfileStatus;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CompanyDTO {
    private Integer id;
    @NotBlank(message = "companyName is valid")
    private String companyName;
    @NotBlank(message = "companyAddress is valid")
    private String companyAddress;
    @NotBlank(message = "companyZipCode is valid")
    private String companyZipCode;
    private LocalDateTime createdDate;
}