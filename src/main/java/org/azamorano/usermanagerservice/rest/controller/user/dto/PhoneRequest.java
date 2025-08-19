package org.azamorano.usermanagerservice.rest.controller.user.dto;

import lombok.Data;

@Data
public class PhoneRequest {

    Long number;

    Integer cityCode;

    Integer countryCode;
}
