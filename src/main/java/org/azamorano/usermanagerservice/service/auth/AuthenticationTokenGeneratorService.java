package org.azamorano.usermanagerservice.service.auth;

import org.azamorano.usermanagerservice.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationTokenGeneratorService {
    String generateToken(User user);

    Boolean validateToken(String token, UserDetails userDetails);
}
