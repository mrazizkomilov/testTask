package com.task.test.service;

import com.task.test.dto.CompanyDTO;
import com.task.test.dto.profile.CompanyDetailDTO;
import com.task.test.entity.CompanyEntity;
import com.task.test.exp.ItemNotFoundException;
import com.task.test.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyDTO create(CompanyDTO dto) {
        CompanyEntity profileEntity = CompanyEntity.conventCompanyEntity(dto);
        profileEntity.setCreatedDate(LocalDateTime.now());
        profileEntity = companyRepository.save(profileEntity);
        dto.setId(profileEntity.getId());

        return dto;
    }
    public CompanyDTO update(CompanyDTO dto) {
        CompanyEntity companyEntity = getEntityById(dto.getId());
        companyEntity.setCompanyName(dto.getCompanyName());
        companyEntity.setCompanyAddress(dto.getCompanyAddress());
        companyEntity.setCompanyZipCode(dto.getCompanyZipCode());

        companyEntity = companyRepository.save(companyEntity);

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(companyEntity.getId());
        companyDTO.setCompanyName(companyEntity.getCompanyName());
        companyDTO.setCompanyAddress(companyEntity.getCompanyAddress());
        companyDTO.setCompanyZipCode(companyEntity.getCompanyZipCode());
        return companyDTO;
    }
    public CompanyDetailDTO getDetail(int id) {
        Optional<CompanyEntity> optional = companyRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("Profile not found");
        }

        return CompanyDetailDTO.convertToDTO(optional.get());

    }
    public CompanyEntity getEntityById(Integer id) {
        Optional<CompanyEntity> optional = companyRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("Profile not found");
        }
        return optional.get();
    }
}
