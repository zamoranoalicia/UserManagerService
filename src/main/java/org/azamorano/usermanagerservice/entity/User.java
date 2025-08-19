package org.azamorano.usermanagerservice.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Entity
@Data
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotNull
    String email;

    @Column
    @NotNull
    String name;

    @Column
    @NotNull
    String password;

    @Column
    @Nullable
    @OneToMany
    List<Phone> phones;

    @CreationTimestamp
    @Column(updatable = false, name = "created_dttm")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_dttm")
    private Date updatedAt;

    @Column(name = "last_login_dttm")
    private Date lastLoginAt;

    @Column(name = "last_used_token")
    @NotNull
    private String lastUsedToken;

    @Column
    private Boolean activeUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
