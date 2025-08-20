package org.azamorano.usermanagerservice.rest.controller.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.azamorano.usermanagerservice.rest.controller.user.dto.LoginRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserResponse;
import org.azamorano.usermanagerservice.rest.controller.user.dto.validator.ErrorResponse;
import org.azamorano.usermanagerservice.service.auth.AuthenticationService;
import org.azamorano.usermanagerservice.service.user.UserCreationException;
import org.azamorano.usermanagerservice.service.user.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> singUpUser(@NotNull @RequestBody @Valid UserRequest userRequest) {
        return new ResponseEntity<>(authenticationService.singUp(userRequest), CREATED);
    }

    @PostMapping("/login")
    public Map<String, String> login(@NotNull @RequestBody @Valid LoginRequest request) {
        return Map.of("token", authenticationService.login(request));
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ErrorResponse> handleUserCreateException(UserCreationException exception) {
        return new ResponseEntity<>(ErrorResponse.builder().message(exception.getMessage()).build(),
                exception.getStatusCode());
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UsernameNotFoundException exception) {
        return new ResponseEntity<>(ErrorResponse.builder().message(exception.getMessage()).build(),
                NOT_FOUND);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(BindException exception) {
        return new ResponseEntity<>(ErrorResponse.builder().message(
                exception.getAllErrors().stream().map( objectError -> objectError.getDefaultMessage()).collect(Collectors.joining())
        ).build(),
                BAD_REQUEST);
    }
}
