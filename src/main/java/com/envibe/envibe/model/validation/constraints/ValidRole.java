package com.envibe.envibe.model.validation.constraints;

import com.envibe.envibe.model.validation.validator.RoleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Bean that allows us to pass a model attribute through our custom RoleValidator class.
// TODO: Replace with built-in Spring ROLE class.
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
@Documented
public @interface ValidRole {
    String message() default "Invalid Role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
