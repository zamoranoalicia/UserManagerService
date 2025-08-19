package org.azamorano.usermanagerservice.service.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class AuthClaim {
    String claimName;
    Object claimValue;
}
