package com.task.test.controller;


import com.task.test.dto.CompanyDTO;
import com.task.test.dto.profile.*;
import com.task.test.enums.ProfileRole;
import com.task.test.exp.ServerBadRequestException;
import com.task.test.service.CompanyService;
import com.task.test.service.ProfileService;
import com.task.test.util.TokenProcess;
import com.task.test.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CompanyDTO dto, HttpServletRequest request) {
        TokenUtil.getCurrentUser(request);
        CompanyDTO companyDTO = companyService.create(dto);
        return ResponseEntity.ok(companyDTO);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getCompanyDetail(@PathVariable int id, HttpServletRequest request) {
        TokenUtil.getCurrentUser(request);
        CompanyDetailDTO dto = companyService.getDetail(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody CompanyDTO dto, HttpServletRequest request) {
        TokenUtil.getCurrentUser(request, ProfileRole.ADMIN);
        CompanyDTO companyDTO = companyService.update(dto);
        return ResponseEntity.ok(companyDTO);
    }
}