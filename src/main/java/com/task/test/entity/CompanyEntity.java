package com.task.test.entity;


import com.task.test.dto.CompanyDTO;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "companyName")
    private String companyName;
    @Column(name = "companyAddress")
    private String companyAddress;
    @Column(name = "companyZipCode")
    private String companyZipCode;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public static CompanyEntity conventCompanyEntity(CompanyDTO dto) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(dto.getCompanyName());
        companyEntity.setCompanyAddress(dto.getCompanyAddress());
        companyEntity.setCompanyZipCode(dto.getCompanyZipCode());
        return companyEntity;
    }

}


