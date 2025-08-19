package org.azamorano.usermanagerservice.rest.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.azamorano.usermanagerservice.entity.User;
import org.springframework.boot.jackson.JsonComponent;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@JsonComponent
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    UUID id;

    LocalDateTime createdAt;

    LocalDateTime modifiedAt;

    LocalDateTime lastLoginAt;

    String accessToken;

    Boolean isActive;

    public static UserResponse of(User user) {

        return UserResponse
                .builder()
                .id(user.getUserId())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getUpdatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .accessToken(user.getLastUsedToken())
                .isActive(user.getActiveUser())
                .build();
    }
}
