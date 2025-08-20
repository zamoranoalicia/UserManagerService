package org.azamorano.usermanagerservice.service.auth;

import lombok.AllArgsConstructor;
import org.azamorano.usermanagerservice.entity.User;
import org.azamorano.usermanagerservice.rest.controller.user.dto.LoginRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserResponse;
import org.azamorano.usermanagerservice.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationTokenGeneratorService authenticationTokenGeneratorService;
    private final AuthenticationManager authenticationManager;

    public UserResponse singUp(UserRequest userRequest) {

        String generatedToken = authenticationTokenGeneratorService.generateToken(userRequest.getEmail());
        User user = User.of(userRequest)
                .toBuilder()
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .activeUser(true)
                .lastLoginAt(LocalDateTime.now())
                .lastUsedToken(generatedToken)
                .build();
        User createdUser = userService.registerUser(user);
        return UserResponse.of(createdUser);
    }

    public String login(LoginRequest request) {
        User user = User.of(request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        return authenticationTokenGeneratorService.generateToken(user);
    }
}
