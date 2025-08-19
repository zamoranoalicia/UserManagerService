package org.azamorano.usermanagerservice.service.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.azamorano.usermanagerservice.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@Service
public class JWTTokenGeneratorService implements AuthenticationTokenGeneratorService {

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private final String apiKey;
    private final Long expirationTime;


    public JWTTokenGeneratorService(@Value("${security.jwt.apiKey}") String apiKey,
                                    @Value("${security.jwt.expiration}") Long expirationTime) {
        this.apiKey = apiKey;
        this.expirationTime = expirationTime;
    }

    @Override
    public String generateToken(User user) {
        List<AuthClaim> claimList = createRequiredClaims(user);
        Date now = new Date(System.currentTimeMillis());
        return Jwts
                .builder()
                .claims(claimList.stream().collect(Collectors.toMap(AuthClaim::getClaimName, AuthClaim::getClaimValue)))
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(calculateExpirationDate(System.currentTimeMillis(), expirationTime))
                .signWith(getSignKey(apiKey))
                .compact();
    }

    private Date calculateExpirationDate(long currentTime, Long expirationTime) {
        return new Date(currentTime + expirationTime);
    }

    private List<AuthClaim> createRequiredClaims(User user) {
        return listOf(
                AuthClaim
                        .builder()
                        .claimName(ID)
                        .claimValue(user.getId().toString())
                        .build(),
                AuthClaim
                        .builder()
                        .claimName(EMAIL)
                        .claimValue(user.getEmail())
                        .build());
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        return null;
    }

    @Override
    public Date getExpirationDate(String token) {
        return null;
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return null;
    }

    private SecretKey getSignKey(String apiKey) {
        byte[] keyBytes = Decoders.BASE64.decode(apiKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
