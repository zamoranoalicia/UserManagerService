package org.azamorano.usermanagerservice.rest.controller.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.azamorano.usermanagerservice.rest.controller.user.dto.validator.ValidEmail;
import org.springframework.boot.jackson.JsonComponent;

@Builder
@Data
@JsonComponent
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull
    @ValidEmail
    String username;

    String password;
}
