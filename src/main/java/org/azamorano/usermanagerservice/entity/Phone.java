package org.azamorano.usermanagerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.azamorano.usermanagerservice.rest.controller.user.dto.PhoneRequest;

import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "phones")
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long phoneId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    User user;

    @Column(name = "phone_number")
    @NotNull
    Long phoneNumber;

    @Column(name = "city_code")
    Integer cityCode;

    @Column(name = "country_code")
    Integer countryCode;

    public static Phone of(PhoneRequest phoneRequest) {
        return Phone.builder()
                .phoneNumber(phoneRequest.getNumber())
                .cityCode(phoneRequest.getCityCode())
                .countryCode(phoneRequest.getCountryCode())
                .build();
    }
}
