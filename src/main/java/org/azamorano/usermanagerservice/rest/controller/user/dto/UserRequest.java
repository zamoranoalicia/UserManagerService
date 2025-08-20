package org.azamorano.usermanagerservice.rest.controller.user.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.azamorano.usermanagerservice.rest.controller.user.dto.validator.Email;
import org.azamorano.usermanagerservice.rest.controller.user.dto.validator.Password;

import java.util.List;

@Data
@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserRequest {

    @NotNull
    @NotEmpty
    String name;

    @NotNull
    @NotEmpty
    @Email
    String email;

    @NotNull
    @NotEmpty
    @Password
    String password;


    List<PhoneRequest> phones;
}
