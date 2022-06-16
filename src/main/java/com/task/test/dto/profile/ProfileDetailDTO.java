package com.task.test.dto.profile;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.task.test.entity.ProfileEntity;
import com.task.test.enums.ProfileRole;
import com.task.test.enums.ProfileStatus;
import lombok.Data;
import org.apache.commons.codec.binary.Hex;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDetailDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String contact;
    private ProfileStatus status;
    private String password;
    private ProfileRole role;
    private LocalDateTime createdDate;
    private String token;

    public static ProfileDetailDTO convertToDTO(ProfileEntity profileEntity) {
        ProfileDetailDTO dto = new ProfileDetailDTO();
        dto.setId(profileEntity.getId());
        dto.setName(profileEntity.getName());
        dto.setSurname(profileEntity.getSurname());
        dto.setEmail(profileEntity.getEmail());
        dto.setContact(profileEntity.getContact());
        dto.setStatus(profileEntity.getStatus());
        dto.setPassword(Hex.encodeHexString(profileEntity.getPassword().getBytes()));
        dto.setRole(profileEntity.getRole());
        dto.setCreatedDate(profileEntity.getCreatedDate());
        return dto;
    }
}