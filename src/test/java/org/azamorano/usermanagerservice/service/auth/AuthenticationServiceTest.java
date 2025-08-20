package org.azamorano.usermanagerservice.service.auth;

import org.azamorano.usermanagerservice.entity.User;
import org.azamorano.usermanagerservice.rest.controller.user.dto.LoginRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserResponse;
import org.azamorano.usermanagerservice.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp_ShouldRegisterUserAndReturnResponse() {
        UserRequest request = new UserRequest("alice", "alice@example.com", "secret123",null);
        User user = User.of(request).toBuilder().userId(UUID.randomUUID()).password("encodedPass").activeUser(true)
                .lastLoginAt(LocalDateTime.now()).lastUsedToken("jwt-token").build();

        when(passwordEncoder.encode("secret123")).thenReturn("encodedPass");
        when(authenticationTokenGeneratorService.generateToken("alice@example.com")).thenReturn("jwt-token");
        when(userService.registerUser(any(User.class))).thenReturn(user);

        UserResponse response = authenticationService.singUp(request);

        assertNotNull(response);
        verify(userService).registerUser(any(User.class));
        verify(passwordEncoder).encode("secret123");
    }

    @Test
    void login_ShouldAuthenticateAndReturnToken() {
        LoginRequest request = new LoginRequest("alice", "secret123");
        User user = new User();
        when(userService.searchUserByUserName("alice")).thenReturn(user);
        when(authenticationTokenGeneratorService.generateToken(user)).thenReturn("jwt-token");

        String token = authenticationService.login(request);

        assertEquals("jwt-token", token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_ShouldThrowException_WhenUsernameOrPasswordMissing() {
        LoginRequest request = new LoginRequest(null, "secret");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authenticationService.login(request));

        assertEquals("Please provide user and password", exception.getMessage());
        verifyNoInteractions(authenticationManager);
    }

    @Test
    void login_ShouldThrowException_WhenAuthenticationFails() {
        LoginRequest request = new LoginRequest("alice", "wrongPass");
        User user = new User();
        when(userService.searchUserByUserName("alice")).thenReturn(user);
        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(BadCredentialsException.class, () -> authenticationService.login(request));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
