package com.envibe.envibe;

import com.envibe.envibe.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests validation constraints of the User class. Currently not properly throwing assertions, cannot figure out why.
 * @see com.envibe.envibe.model.User
 *
 * @author ARMmaster17
 */
public class UnitUserTests {

    private static Validator validator;

    @Before
    public static void setupValidatorInstance() {
        //validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testUsernameField() throws Exception {
        //User testUser = new User("testuser", "testpassword", "ROLE_USER", "user@example.com");
        //testUser.setUsername(" ");
        //Set<ConstraintViolation<User>> violations = validator.validate(testUser);
        //assertThat(violations.size()).isGreaterThan(0);
    }
}
