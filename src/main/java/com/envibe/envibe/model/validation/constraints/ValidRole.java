package com.envibe.envibe.model.validation.constraints;

import com.envibe.envibe.model.validation.validator.RoleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// TODO: Replace with built-in Spring ROLE class.

/**
 * Bean that requires a field to possess a valid role attribute as distributed by the Spring Security framework.
 * Mainly contains some required attributes for the Spring Security framework. See {@link RoleValidator} for the actual implementation.
 *
 * @author ARMmaster17
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
@Documented
public @interface ValidRole {
    String message() default "Invalid Role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
