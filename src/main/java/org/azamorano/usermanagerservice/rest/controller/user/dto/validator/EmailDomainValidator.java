package org.azamorano.usermanagerservice.rest.controller.user.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class EmailDomainValidator implements ConstraintValidator<Email, String> {

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    String domain;


    public EmailDomainValidator(@Value("${config.user.domain}") String domain) {
        this.domain = domain;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (!Optional.ofNullable(email).isPresent() || !EMAIL_REGEX.matcher(email).matches()) {
            return false;
        }
        return email.contains(domain);
    }
}
