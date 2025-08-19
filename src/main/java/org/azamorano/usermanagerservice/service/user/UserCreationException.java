package org.azamorano.usermanagerservice.service.user;

public class UserCreationException extends RuntimeException {
    String message;
    public UserCreationException(String message) {
        super(message);
        this.message = message;
    }

}
