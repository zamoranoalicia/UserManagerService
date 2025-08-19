package org.azamorano.usermanagerservice.rest.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
@JsonComponent
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    UUID id;

    Date createdAt;

    Date modifiedAt;

    Date lastLoginAt;

    String accessToken;

    Boolean isActive;

    public static UserResponse of(UserRequest userRequest) {
        return UserResponse.builder().build();
    }
}
