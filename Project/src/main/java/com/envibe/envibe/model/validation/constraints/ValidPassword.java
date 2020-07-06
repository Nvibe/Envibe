package com.envibe.envibe.model.validation.constraints;

import com.envibe.envibe.model.validation.validator.PasswordValidator;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Bean that requires that a finalized hashed password meets the internal SQL table schema requirements to be stored correctly.
 *
 * @author ARMmaster17
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
    /**
     * Default error message on constraint validation error.
     * @return Error message.
     */
    String message() default "Internal error occurred during password hashing";

    /**
     * Validation groups allowed for this constraint.
     * @return Group array.
     */
    Class<?>[] groups() default {};

    /**
     * Allows custom payload specification through the Bean Validation 2.0 API.
     * @return Payload extension class.
     */
    Class<? extends Payload>[] payload() default {};
}
