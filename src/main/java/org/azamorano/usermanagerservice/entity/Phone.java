package org.azamorano.usermanagerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long phoneId;
    @Column
    Integer phoneNumber;
    @Column
    Integer cityCode;
    @Column
    Integer countryCode;
}
