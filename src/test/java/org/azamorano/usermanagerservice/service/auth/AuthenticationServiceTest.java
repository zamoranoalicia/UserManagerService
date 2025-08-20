package org.azamorano.usermanagerservice.service.auth;

import org.azamorano.usermanagerservice.entity.User;
import org.azamorano.usermanagerservice.rest.controller.user.dto.LoginRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.PhoneRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserResponse;
import org.azamorano.usermanagerservice.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationTokenGeneratorService authenticationTokenGeneratorService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void signUp_ShouldRegisterUserAndReturnResponse() {
        // Use the example JSON data
        UserRequest request = new UserRequest(
                "Juan Rodriguez",
                "juan@dominio.cl",
                "Abcdef1@",
                List.of(new PhoneRequest(Long.valueOf(1234567), 1, 57))
        );

        User user = User.of(request).toBuilder()
                .userId(UUID.randomUUID())
                .password("encodedPass")
                .activeUser(true)
                .lastLoginAt(LocalDateTime.now())
                .lastUsedToken("jwt-token")
                .build();

        when(passwordEncoder.encode("Abcdef1@")).thenReturn("encodedPass");
        when(authenticationTokenGeneratorService.generateToken("juan@dominio.cl")).thenReturn("jwt-token");
        when(userService.registerUser(any(User.class))).thenReturn(user);

        UserResponse response = authenticationService.singUp(request);

        assertNotNull(response);
        verify(userService).registerUser(any(User.class));
        verify(passwordEncoder).encode("Abcdef1@");
    }

    @Test
    void login_ShouldAuthenticateAndReturnToken() {
        LoginRequest request = new LoginRequest("juan@dominio.cl", "Abcdef1@");
        User user = new User();

        when(userService.searchUserByUserName("juan@dominio.cl")).thenReturn(user);
        when(authenticationTokenGeneratorService.generateToken(user)).thenReturn("jwt-token");

        String token = authenticationService.login(request);

        assertEquals("jwt-token", token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_ShouldThrowException_WhenUsernameOrPasswordMissing() {
        LoginRequest request = new LoginRequest(null, "Abcdef1@");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authenticationService.login(request));

        assertEquals("Please provide user and password", exception.getMessage());
        verifyNoInteractions(authenticationManager);
    }

    @Test
    void login_ShouldThrowException_WhenAuthenticationFails() {
        LoginRequest request = new LoginRequest("juan@dominio.cl", "wrongPass");
        User user = new User();
        when(userService.searchUserByUserName("juan@dominio.cl")).thenReturn(user);
        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(BadCredentialsException.class, () -> authenticationService.login(request));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
