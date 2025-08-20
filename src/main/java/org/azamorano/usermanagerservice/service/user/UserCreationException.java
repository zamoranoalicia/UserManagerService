package org.azamorano.usermanagerservice.service.user;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserCreationException extends RuntimeException {
    String message;
    HttpStatus statusCode;
    public UserCreationException(String message, HttpStatus statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

}
