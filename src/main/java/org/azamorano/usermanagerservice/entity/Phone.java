package org.azamorano.usermanagerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long phoneId;

    @Column(name = "phone_number")
    @NotNull
    Integer phoneNumber;

    @Column(name = "city_code")
    Integer cityCode;

    @Column(name = "country_code")
    Integer countryCode;
}
