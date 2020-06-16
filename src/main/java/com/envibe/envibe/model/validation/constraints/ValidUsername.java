package com.envibe.envibe.model.validation.constraints;

import com.envibe.envibe.model.validation.validator.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Bean that verifies that a given username can function as an internal tag and will not interfere with standard database operation.
 *
 * @author ARMmaster17
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface ValidUsername {

    /**
     * Default error message on constraint validation error.
     * @return Error message.
     */
    String message() default "Username contains prohibited characters";

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
