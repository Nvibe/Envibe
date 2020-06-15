package com.envibe.envibe.model.validation.validator;

import com.envibe.envibe.model.validation.constraints.ValidRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    @Override
    public void initialize(ValidRole constraintAnnotation) {

    }

    // Checks if a given role matches our master list of all roles across the site.
    @Override
    public boolean isValid(String role, ConstraintValidatorContext context) {
        if (role.equals("ROLE_USER")) {
            return true;
        }
        else return role.equals("ROLE_ARTIST");
    }
}
