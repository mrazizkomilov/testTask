package com.task.test.service;

import com.task.test.dto.AuthorizationDTO;
import com.task.test.dto.RegistrationDTO;
import com.task.test.dto.profile.ProfileDetailDTO;
import com.task.test.dto.profile.UserDetails;
import com.task.test.entity.ProfileEntity;
import com.task.test.enums.ProfileRole;
import com.task.test.enums.ProfileStatus;
import com.task.test.exp.ItemNotFoundException;
import com.task.test.exp.ServerBadRequestException;
import com.task.test.repository.ProfileRepository;
import com.task.test.util.TokenProcess;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
public class AuthService {
    @Value("${server.url}")
    private String serverUrl;

    private final ProfileRepository profileRepository;
    private final MailSenderService mailSenderService;
    private final ResourceBundleMessageSource messageSource;

    public AuthService(ProfileRepository profileRepository, MailSenderService mailSenderService, ResourceBundleMessageSource messageSource) {
        this.profileRepository = profileRepository;
        this.mailSenderService = mailSenderService;
        this.messageSource = messageSource;
    }

    public ProfileDetailDTO auth(AuthorizationDTO dto, String lang) {
        Optional<ProfileEntity> optionalProfileEntity = profileRepository.findByEmailAndPassword(dto.getEmail(), DigestUtils.md5Hex(dto.getPassword()));

        if (!optionalProfileEntity.isPresent()) {
            throw new ItemNotFoundException(messageSource.getMessage("item.not.found", null, new Locale(lang)));
        }

        ProfileEntity profileEntity = optionalProfileEntity.get();
        if (!profileEntity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new RuntimeException("Profile not active");
        }

        UserDetails userDetails = new UserDetails();
        userDetails.setId(profileEntity.getId());
        userDetails.setName(profileEntity.getName());
        userDetails.setRole(profileEntity.getRole());

        String jwt = TokenProcess.encodeJwt(userDetails);

        ProfileDetailDTO responseDTO = new ProfileDetailDTO();
        responseDTO.setToken(jwt);
        responseDTO.setName(profileEntity.getName());
        responseDTO.setSurname(profileEntity.getSurname());
        responseDTO.setContact(profileEntity.getContact());

        return responseDTO;
    }

    public String registration(RegistrationDTO registrationDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(registrationDTO.getEmail());
        if (optional.isPresent()) {
            throw new ServerBadRequestException("Email already exists");
        }

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(registrationDTO.getName());
        profileEntity.setSurname(registrationDTO.getSurname());
        profileEntity.setEmail(registrationDTO.getEmail());
        profileEntity.setContact(registrationDTO.getContact());
        profileEntity.setRole(ProfileRole.USER);
        profileEntity.setStatus(ProfileStatus.INACTIVE);
        profileEntity.setCreatedDate(LocalDateTime.now());
        profileEntity.setPassword(DigestUtils.md5Hex(registrationDTO.getPassword()));

        profileEntity = profileRepository.save(profileEntity);

        String jwt = TokenProcess.encodeJwt(profileEntity.getId());
        String link = serverUrl + "/auth/" + "verification/" + jwt;

        try {
            mailSenderService.sendEmail(registrationDTO.getEmail(), "hi", " ==>" + link);
        } catch (Exception e) {
            profileRepository.delete(profileEntity);
            throw new RuntimeException(e.getMessage());
        }

        return "Succesfully! Message sent to your email";
    }

    public String verification(String jwt) {
        Integer profileId = TokenProcess.decodeJwtForId(jwt);
        Optional<ProfileEntity> optional = profileRepository.findById(profileId);
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("Profile not found. Wrong verification");
        }
        ProfileEntity profileEntity = optional.get();

        if (!profileEntity.getStatus().equals(ProfileStatus.INACTIVE)) {
            throw new ServerBadRequestException("Your status is wrong");
        }

        profileEntity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profileEntity);
        return "Succesfully! Verified";
    }
}
