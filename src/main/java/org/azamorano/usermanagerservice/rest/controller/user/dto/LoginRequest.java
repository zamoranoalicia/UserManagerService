package org.azamorano.usermanagerservice.rest.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;

@Builder
@Data
@JsonComponent
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    String username;

    String password;
}
