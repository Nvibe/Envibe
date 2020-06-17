package com.envibe.envibe.model.validation.validator;

import com.envibe.envibe.model.validation.constraints.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Validator for fields marked with the @ValidUsername annotation.
 *
 * @author ARMmaster17
 */
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    /**
     * Initializer for UsernameValidator object. Contains no logic, but is required by base ConstraintValidator interface.
     * @param constraintAnnotation Additional information provided by the JavaX framework.
     */
    @Override
    public void initialize(ValidUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(@NotNull String username, ConstraintValidatorContext context) {
        Objects.requireNonNull(username, "Username in User model cannot be null");
        // Check that no spaces are present.
        if(username.contains(" ")) return false;
        // Check that the special tag separator '|' does not exist.
        if(username.contains("|")) return false;
        // Check that the username will fit in the table schema.
        if(username.length() >= 255) return false;
        // Check that the username is not empty.
        if(username.isEmpty()) return false;
        // Check that the username is URL safe.
        if(username.contains("!")) return false;
        if(username.contains("@")) return false;
        if(username.contains("#")) return false;
        if(username.contains("%")) return false;
        if(username.contains("^")) return false;
        if(username.contains("&")) return false;
        if(username.contains("?")) return false;
        if(username.contains(":")) return false;
        if(username.contains("/")) return false;
        if(username.contains("\\")) return false;
        // All checks passed. Return success.
        return true;
    }
}
