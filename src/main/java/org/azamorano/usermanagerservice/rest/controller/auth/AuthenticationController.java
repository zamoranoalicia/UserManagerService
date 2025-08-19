package org.azamorano.usermanagerservice.rest.controller.auth;

import org.azamorano.usermanagerservice.rest.controller.user.dto.LoginRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserResponse;
import org.azamorano.usermanagerservice.service.auth.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/singUp")
    public UserResponse singUpUser(@RequestBody UserRequest userRequest) {
        return authenticationService.signUp(userRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}
