package com.task.test.dto.profile;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.task.test.entity.CompanyEntity;
import com.task.test.entity.ProfileEntity;
import com.task.test.enums.ProfileRole;
import com.task.test.enums.ProfileStatus;
import lombok.Data;
import org.apache.commons.codec.binary.Hex;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDetailDTO {
    private Integer id;
    private String companyName;
    private String companyAddress;
    private String companyZipCode;

    public static CompanyDetailDTO convertToDTO(CompanyEntity companyEntity) {
        CompanyDetailDTO dto = new CompanyDetailDTO();
        dto.setId(companyEntity.getId());
        dto.setCompanyName(companyEntity.getCompanyName());
        dto.setCompanyAddress(companyEntity.getCompanyZipCode());
        dto.setCompanyZipCode(companyEntity.getCompanyAddress());
        return dto;
    }
}