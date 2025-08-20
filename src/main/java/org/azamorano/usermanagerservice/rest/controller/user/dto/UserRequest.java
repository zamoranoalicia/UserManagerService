package org.azamorano.usermanagerservice.rest.controller.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.azamorano.usermanagerservice.rest.controller.user.dto.validator.ValidEmail;
import org.azamorano.usermanagerservice.rest.controller.user.dto.validator.Password;

import java.util.List;

@Data
@Value
@Builder
@AllArgsConstructor
public class UserRequest {

    @NotNull
    @NotEmpty(message = "name value must not be empty")
    String name;

    @NotNull
    @NotEmpty(message = "email value must not be empty")
    @ValidEmail
    String email;

    @NotNull
    @NotEmpty(message = "password value must not be empty")
    @Password
    String password;

    List<PhoneRequest> phones;
}
