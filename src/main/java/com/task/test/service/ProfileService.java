package com.task.test.service;

import com.task.test.dto.profile.*;
import com.task.test.entity.ProfileEntity;
import com.task.test.enums.ProfileRole;
import com.task.test.enums.ProfileStatus;
import com.task.test.exp.ItemNotFoundException;
import com.task.test.exp.MethodNotAllowedExc;
import com.task.test.exp.ServerBadRequestException;
import com.task.test.repository.ProfileRepository;
import com.task.test.util.TokenProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Value("${server.url}")
    private String serverUrl;

    private final ProfileRepository profileRepository;
    private final MailSenderService mailSenderService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, MailSenderService mailSenderService) {
        this.profileRepository = profileRepository;
        this.mailSenderService = mailSenderService;
    }

    public ProfileCreateDTO create(ProfileCreateDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new ServerBadRequestException("Email already exists");
        }

        ProfileEntity profileEntity = ProfileEntity.convertToEntity(dto);
        profileEntity.setCreatedDate(LocalDateTime.now());
        profileEntity.setRole(ProfileRole.USER);
        profileEntity = profileRepository.save(profileEntity);
        dto.setId(profileEntity.getId());

        return dto;
    }

    public ProfileCreateDTO createByAdmin(ProfileCreateDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new ServerBadRequestException("Email already exists");
        }

        ProfileEntity profileEntity = ProfileEntity.convertToEntity(dto);
        profileEntity.setCreatedDate(LocalDateTime.now());
        if (profileEntity.getRole().equals(ProfileRole.USER)) {
            throw new MethodNotAllowedExc("Method not allowed");
        }
        profileEntity = profileRepository.save(profileEntity);
        dto.setId(profileEntity.getId());

        return dto;
    }

    public ProfileDetailDTO update(Integer id, ProfileUpdateDTO dto) {
        ProfileEntity profileEntity = getEntityById(id);
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setContact(dto.getContact());

        profileEntity = profileRepository.save(profileEntity);

        ProfileDetailDTO profileDetailDTO = new ProfileDetailDTO();
        profileDetailDTO.setId(profileEntity.getId());
        profileDetailDTO.setName(profileEntity.getName());
        profileDetailDTO.setSurname(profileEntity.getSurname());
        profileDetailDTO.setContact(profileEntity.getContact());
        return profileDetailDTO;
    }

    public void delete(Integer id, ProfileUpdateDTO dto) {
        ProfileEntity profileEntity = getEntityById(id);
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setContact(dto.getContact());


        profileRepository.delete(profileEntity);
    }

    public Optional<ProfileEntity> findById(Integer id) {
        return profileRepository.findById(id);
    }



    public Page<ProfileDetailDTO> filter(ProfileFilterDTO pfdto) {
        String sotyBy = pfdto.getSortBy();
        if (sotyBy == null || sotyBy.isEmpty()) {
            sotyBy = "createdDate";
        }
        Pageable pageable = PageRequest.of(pfdto.getPage(), pfdto.getSize(), pfdto.getDirection(), sotyBy);

        List<Predicate> predicateList = new ArrayList<>();
        Specification<ProfileEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            if (pfdto.getName() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("name"), pfdto.getName()));
            }
            if (pfdto.getSurname() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("surname"), pfdto.getSurname()));
            }
            if (pfdto.getEmail() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("email"), pfdto.getEmail()));
            }
            if (pfdto.getContact() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("contact"), pfdto.getContact()));
            }
            if (pfdto.getNameList() != null && pfdto.getNameList().size() > 0) {
                predicateList.add(criteriaBuilder.or(root.get("name").in(pfdto.getNameList())));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

        Page<ProfileEntity> profileEntityPage = profileRepository.findAll(specification, pageable);

        List<ProfileDetailDTO> profileDetailDTOList = profileEntityPage.getContent().stream().map(ProfileDetailDTO::convertToDTO).collect(Collectors.toList());
        return new PageImpl<>(profileDetailDTOList, pageable, profileEntityPage.getTotalElements());
    }

    public ProfileDetailDTO getDetail(UserDetails userDetails) {
        Optional<ProfileEntity> optional = profileRepository.findById(userDetails.getId());
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("Profile not found");
        }

        return ProfileDetailDTO.convertToDTO(optional.get());

    }

    public String updateEmail(Integer id, String email) {
        ProfileEntity profileEntity = getEntityById(id);

        if (!profileEntity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new ServerBadRequestException("Your status is wrong");
        }

        UpdateEmailDTO updateEmailDTO = new UpdateEmailDTO();
        updateEmailDTO.setId(id);
        updateEmailDTO.setEmail(email);

        String jwt = TokenProcess.encodeJwt(updateEmailDTO);
        String link = serverUrl + "/auth/" + "updateEmail/" + jwt;

        try {
            mailSenderService.sendEmail(email, "Change email", link);
        } catch (Exception e) {
            return "Error on sending message to " + email;
        }

        return "Succesfully! Message sent to " + email;
    }

    public String updateEmailVer(UpdateEmailDTO updateEmailDTO) {
        ProfileEntity profileEntity = getEntityById(updateEmailDTO.getId());
        profileEntity.setEmail(updateEmailDTO.getEmail());
        profileRepository.save(profileEntity);
        return "Succesfully! Changed email";
    }

    public ProfileEntity getEntityById(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("Profile not found");
        }
        return optional.get();
    }

}
