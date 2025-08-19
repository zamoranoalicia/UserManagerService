package org.azamorano.usermanagerservice.rest.controller.user.dto.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ErrorResponse {
    String message;
}
