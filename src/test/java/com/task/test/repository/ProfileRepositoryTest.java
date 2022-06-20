package com.task.test.repository;

import com.task.test.dto.RegistrationDTO;
import com.task.test.entity.ProfileEntity;
import com.task.test.enums.ProfileRole;
import com.task.test.enums.ProfileStatus;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository underTest;
    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void findByEmail() {
        String email = "mrazizbekkomilov@gmail.com";
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("Azizbek");
        registrationDTO.setSurname("Komilov");
        registrationDTO.setEmail("mrazizbekkomilov@gmail.com");
        registrationDTO.setContact("+998989999989");
        registrationDTO.setPassword("998989999989");
        registrationDTO.setCompanyId("2");

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(registrationDTO.getName());
        profileEntity.setSurname(registrationDTO.getSurname());
        profileEntity.setEmail(registrationDTO.getEmail());
        profileEntity.setContact(registrationDTO.getContact());
        profileEntity.setRole(ProfileRole.USER);
        profileEntity.setStatus(ProfileStatus.INACTIVE);
        profileEntity.setCreatedDate(LocalDateTime.now());
        profileEntity.setPassword(DigestUtils.md5Hex(registrationDTO.getPassword()));

        underTest.save(profileEntity);
        Optional<ProfileEntity> optional = underTest.findByEmail(email);
        assertTrue(optional.isPresent());
    }

    @Test
    void findByEmailAndPassword() {
        String email = "mrazizbekkomilov@gmail.com";
        String password = "998989999989";
        Optional<ProfileEntity> optional = underTest.findByEmailAndPassword(email, password);
        assertFalse(optional.isPresent());
    }
}