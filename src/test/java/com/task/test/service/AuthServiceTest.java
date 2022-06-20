package com.task.test.service;

import com.task.test.dto.RegistrationDTO;
import com.task.test.entity.ProfileEntity;
import com.task.test.enums.ProfileRole;
import com.task.test.enums.ProfileStatus;
import com.task.test.repository.ProfileRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private ProfileRepository profileRepository;
    private AuthService underTest;
    private  MailSenderService mailSenderService;
    private  ResourceBundleMessageSource messageSource;
    @BeforeEach
    void setUp(){
        underTest = new AuthService(profileRepository, mailSenderService, messageSource);
    }

    @Test
    void auth() {

    }

    @Test
    void registration() {
    }
}