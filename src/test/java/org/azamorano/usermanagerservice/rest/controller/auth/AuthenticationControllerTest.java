package org.azamorano.usermanagerservice.rest.controller.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.azamorano.usermanagerservice.rest.controller.user.dto.LoginRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserResponse;
import org.azamorano.usermanagerservice.service.auth.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    private AuthenticationService authenticationService;
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        authenticationService = mock(AuthenticationService.class);
        authenticationController = new AuthenticationController(authenticationService);
    }

    @Nested
    @DisplayName("singUpUser method")
    class SingUpUser {

        @Test
        @DisplayName("should return UserResponse when valid UserRequest is provided")
        void shouldReturnUserResponseWhenValidUserRequestProvided() throws JsonProcessingException {
            UserRequest userRequest = UserRequest.builder().build();
            ResponseEntity<UserResponse> expectedResponse =
                    new ResponseEntity<>(UserResponse.builder().build(), CREATED);

            when(authenticationService.singUp(userRequest)).thenReturn(UserResponse.builder().build());

            ResponseEntity<UserResponse> actualResponse = authenticationController.singUpUser(userRequest);

            assertEquals(expectedResponse, actualResponse);
            verify(authenticationService, times(1)).singUp(userRequest);
        }

        @Test
        @DisplayName("should throw exception when UserRequest is null")
        void shouldThrowExceptionWhenUserRequestIsNull() {
            UserRequest userRequest = null;

            assertThrows(IllegalArgumentException.class, () -> authenticationController.singUpUser(userRequest));
            verify(authenticationService, never()).singUp(any());
        }
    }

    @Nested
    @DisplayName("login method")
    class Login {

        @Test
        @DisplayName("should return token when valid LoginRequest is provided")
        void shouldReturnTokenWhenValidLoginRequestProvided() {
            LoginRequest loginRequest = new LoginRequest("username", "password");
            String expectedToken = "valid-token";

            when(authenticationService.login(loginRequest)).thenReturn(expectedToken);

            String actualToken = authenticationController.login(loginRequest).toString();

            assertEquals(expectedToken, actualToken);
            verify(authenticationService, times(1)).login(loginRequest);
        }

        @Test
        @DisplayName("should throw exception when LoginRequest is null")
        void shouldThrowExceptionWhenLoginRequestIsNull() {
            LoginRequest loginRequest = null;

            assertThrows(IllegalArgumentException.class, () -> authenticationController.login(loginRequest));
            verify(authenticationService, never()).login(any());
        }
    }
}