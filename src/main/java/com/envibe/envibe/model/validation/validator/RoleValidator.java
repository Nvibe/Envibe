package com.envibe.envibe.model.validation.validator;

import com.envibe.envibe.model.validation.constraints.ValidRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * Validator for fields marked with the @ValidRole annotation.
 *
 * @author ARMmaster17
 */
public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    /**
     * Master list of accepted values for the user_role field.
     */
    private static final String[] VALID_ROLES = {"ROLE_USER", "ROLE_ADMIN"};

    /**
     * Initializer for RoleValidator object. Contains no logic, but is required by base ConstraintValidator interface.
     * @param constraintAnnotation Additional information provided by the JavaX framework.
     */
    @Override
    public void initialize(ValidRole constraintAnnotation) {

    }

    /**
     * Validation function that analyzes the current value of a given field with the @ValidRole annotation.
     * @param role Current value of the annotated field.
     * @param context Additional information supplied by the JavaX framework.
     * @return If the supplied value matches the function-specific constraints.
     */
    @Override
    public boolean isValid(@NotNull String role, ConstraintValidatorContext context) {
        Objects.requireNonNull(role, "Method argument role cannot be null");
        return (Arrays.asList(VALID_ROLES).contains(role));
    }
}
