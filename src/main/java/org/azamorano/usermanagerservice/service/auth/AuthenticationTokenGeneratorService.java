package org.azamorano.usermanagerservice.service.auth;

import org.azamorano.usermanagerservice.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface AuthenticationTokenGeneratorService {
    String generateToken(User user);
    String generateToken(String email);

    Boolean validateToken(String token, UserDetails userDetails);

    Date getExpirationDate(String token);

    Boolean isTokenExpired(String token);

    String extractUserName(String token);
}
