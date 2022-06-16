package com.task.test.entity;


import com.task.test.dto.ProfileDTO;
import com.task.test.dto.profile.ProfileCreateDTO;
import com.task.test.enums.ProfileRole;
import com.task.test.enums.ProfileStatus;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email", unique = true, nullable = false)  // = login
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "contact")
    private String contact;
    @Column(name = "login")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


    public static ProfileEntity convertToEntity(ProfileDTO dto) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setStatus(dto.getStatus());
        profileEntity.setPassword(DigestUtils.md5Hex(dto.getPassword()));
        profileEntity.setEmail(dto.getEmail());
        profileEntity.setContact(dto.getContact());
        profileEntity.setRole(dto.getRole());
        return profileEntity;
    }
    public static ProfileEntity convertToEntity(ProfileCreateDTO dto) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setStatus(ProfileStatus.ACTIVE);
        profileEntity.setPassword(DigestUtils.md5Hex(dto.getPassword()));
        profileEntity.setEmail(dto.getEmail());
        profileEntity.setContact(dto.getContact());
        profileEntity.setRole(dto.getRole());
        return profileEntity;
    }
}
