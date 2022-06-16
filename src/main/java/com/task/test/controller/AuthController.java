package com.task.test.controller;


import com.task.test.dto.AuthorizationDTO;
import com.task.test.dto.RegistrationDTO;
import com.task.test.dto.profile.ProfileDetailDTO;
import com.task.test.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/authorization")
    public ResponseEntity<?> auth(@Valid @RequestBody AuthorizationDTO dto, @RequestHeader( value = "Accept-Language", defaultValue = "uz") String lang) {
        log.info("Authorization: {} " +dto);
        ProfileDetailDTO result = authService.auth(dto , lang);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDTO dto) {
        String result = authService.registration(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        String result = authService.verification(jwt);
        return ResponseEntity.ok(result);
    }
}
