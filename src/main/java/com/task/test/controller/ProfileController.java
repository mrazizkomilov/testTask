package com.task.test.controller;


import com.task.test.dto.profile.*;
import com.task.test.enums.ProfileRole;
import com.task.test.exp.ServerBadRequestException;
import com.task.test.service.ProfileService;
import com.task.test.util.TokenProcess;
import com.task.test.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ProfileCreateDTO dto, HttpServletRequest request) {
        TokenUtil.getCurrentUser(request);
        ProfileCreateDTO profileCreateDTO = profileService.create(dto);
        return ResponseEntity.ok(profileCreateDTO);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getProfileDetail(HttpServletRequest request) {
        UserDetails userDetails = TokenUtil.getCurrentUser(request);
        ProfileDetailDTO dto = profileService.getDetail(userDetails);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/detail")
    public ResponseEntity<?> update(@Valid @RequestBody ProfileUpdateDTO dto, HttpServletRequest request) {
        UserDetails userDetails = TokenUtil.getCurrentUser(request);
        ProfileDetailDTO profileDetailDTO = profileService.update(userDetails.getId(), dto);
        return ResponseEntity.ok(profileDetailDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@Valid @RequestBody ProfileUpdateDTO dto, HttpServletRequest request) {
        UserDetails userDetails = TokenUtil.getCurrentUser(request);
        profileService.delete(userDetails.getId(), dto);
        return ResponseEntity.ok().body("");
    }

    @PutMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestParam("email") String email, HttpServletRequest request) {
        UserDetails userDetails = TokenUtil.getCurrentUser(request);
        String result = profileService.updateEmail(userDetails.getId(), email);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/updateEmail/{jwt}")
    public ResponseEntity<?> updateEmailVeri(@PathVariable("jwt") String jwt, HttpServletRequest request){
        UserDetails userDetails = TokenUtil.getCurrentUser(request);
        UpdateEmailDTO updateEmailDTO = TokenProcess.decodeJwt2(jwt);
        if (!userDetails.getId().equals(updateEmailDTO.getId())){
            throw new ServerBadRequestException("Error on updating email");
        }
        String result = profileService.updateEmailVer(updateEmailDTO);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/adm")
    public ResponseEntity<?> createbyAdmin(@Valid @RequestBody ProfileCreateDTO dto, HttpServletRequest request) {
        TokenUtil.getCurrentUser(request, ProfileRole.ADMIN);
        ProfileCreateDTO profileCreateDTO = profileService.createByAdmin(dto);
        return ResponseEntity.ok(profileCreateDTO);
    }

    @PostMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ProfileUpdateDTO dto, HttpServletRequest request) {
        TokenUtil.getCurrentUser(request, ProfileRole.ADMIN);
        ProfileDetailDTO profileDetailDTO = profileService.update(id, dto);
        return ResponseEntity.ok(profileDetailDTO);
    }
}
