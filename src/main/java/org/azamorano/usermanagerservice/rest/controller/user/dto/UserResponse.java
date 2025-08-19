package org.azamorano.usermanagerservice.rest.controller.user.dto;

import lombok.Data;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Date;
import java.util.UUID;

@Data
@JsonComponent
public class UserResponse {

    UUID id;

    Date createdAt;

    Date modifiedAt;

    Date lastLoginAt;

    String accessToken;

    Boolean isActive;
}
