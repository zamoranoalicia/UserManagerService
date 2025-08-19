package org.azamorano.usermanagerservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.azamorano.usermanagerservice.rest.controller.user.dto.LoginRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.PhoneRequest;
import org.azamorano.usermanagerservice.rest.controller.user.dto.UserRequest;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Builder(toBuilder = true)
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(unique = true, nullable = false)
    String email;

    @Column
    @NotNull
    String name;

    @Column
    @NotNull
    String password;

    @Column
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    List<Phone> phones;

    @CreationTimestamp
    @Column(updatable = false, name = "created_dttm")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_dttm")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_dttm")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_used_token")
    @NotNull
    private String lastUsedToken;

    @Column
    private Boolean activeUser;

    public static User of(UserRequest userRequest) {
        return User
                .builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .phones(getPhoneEntities(userRequest.getPhones()))
                .build();
    }

    private static List<Phone> getPhoneEntities(List<PhoneRequest> phones) {
        return phones.stream().map(Phone::of).collect(Collectors.toList());
    }

    public static User of(LoginRequest request) {
        return User
                .builder()
                .email(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
