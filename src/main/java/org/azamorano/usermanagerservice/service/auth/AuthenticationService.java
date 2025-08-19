package org.azamorano.usermanagerservice.service.auth;

import lombok.AllArgsConstructor;
import org.azamorano.usermanagerservice.entity.User;
import org.azamorano.usermanagerservice.persistence.user.UserRepository;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserRequest;
import org.azamorano.usermanagerservice.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public User signUp(UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

}
