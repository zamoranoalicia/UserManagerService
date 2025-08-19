package org.azamorano.usermanagerservice.rest.controller.user.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.azamorano.usermanagerservice.rest.controller.user.dto.validator.Email;
import org.azamorano.usermanagerservice.rest.controller.user.dto.validator.Password;

import java.util.List;

@Data
public class UserRequest {

    @NotNull
    String name;
    @NotNull
    @Email
    String email;
    @NotNull
    @Password
    String password;
    @Nullable
    List<PhoneRequest> phones;
}
