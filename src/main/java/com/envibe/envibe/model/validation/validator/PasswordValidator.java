package com.envibe.envibe.model.validation.validator;

import com.envibe.envibe.model.validation.constraints.ValidPassword;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Checks that final hashed password will not cause internal SQL errors.
 *
 * @author ARMmaster17
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    /**
     * Initializer for HashedPasswordValidator object. Contains no logic, but is required by base ConstraintValidator interface.
     * @param constraintAnnotation Additional information provided by the JavaX framework.
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    /**
     * Validation function that analyzes the current value of a given field with the @ValidHashedPassword annotation.
     * @param hashedPassword Current value of the annotated field.
     * @param context Additional information supplied by the JavaX framework.
     * @return If the supplied value matches the function-specific constraints.
     */
    @Override
    public boolean isValid(@NotNull String hashedPassword, ConstraintValidatorContext context) {
        Objects.requireNonNull(hashedPassword, "Method argument hashedPassword cannot be null");
        // Check if the hash is empty.
        if(hashedPassword.isEmpty()) return false;
        // Check if the hash is too long.
        if(hashedPassword.length() >= 255) return false;
        // Checks passed. Return success.
        return true;
    }
}
