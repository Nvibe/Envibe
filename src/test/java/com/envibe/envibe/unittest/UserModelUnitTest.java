package com.envibe.envibe.unittest;

import com.envibe.envibe.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests get/set model functions of User class.
 * @see com.envibe.envibe.model.User
 *
 * @author ARMmaster17
 */
public class UserModelUnitTest {

    private static User testUser;

    private static final String TEST_USERNAME = "user1";
    private static final String TEST_PASSWORD = "no-hash";
    private static final String TEST_ROLE = "ROLE_USER";
    private static final String TEST_EMAIL = "user1@example.com";
    private static final String TEST_COUNTRY = "United States";
    private static final Date TEST_BIRTHDAY = new Date("1999-01-28");
    private static final String TEST_LASTNAME = "userlastname";
    private static final String TEST_FIRSTNAME = "userfirstname";
    private static final String TEST_STRING = "test";
    private static final Date TEST_DATE = new Date("2005-01-01");


    @BeforeEach
    public static void setupTestUserInstance() {
        testUser = new User(TEST_USERNAME, TEST_PASSWORD, TEST_ROLE, TEST_EMAIL, TEST_COUNTRY, TEST_BIRTHDAY, TEST_LASTNAME, TEST_FIRSTNAME);
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        testUser = new User();
    }

    @Test
    public void testUsernameField() throws Exception {
        testUser.setUsername(TEST_STRING);
        assertThat(testUser.getUsername()).isEqualTo(TEST_STRING);
    }

    @Test
    public void testPasswordField() throws Exception {
        testUser.setPassword(TEST_STRING);
        assertThat(testUser.getPassword()).isEqualTo(TEST_STRING);
    }

    @Test
    public void testRoleField() throws Exception {
        testUser.setRole(TEST_STRING);
        assertThat(testUser.getRole()).isEqualTo(TEST_STRING);
    }

    @Test
    public void testEmailField() throws Exception {
        testUser.setEmail(TEST_STRING);
        assertThat(testUser.getEmail()).isEqualTo(TEST_STRING);
    }

    @Test
    public void testCountryField() throws Exception {
        testUser.setCountry(TEST_STRING);
        assertThat(testUser.getCountry()).isEqualTo(TEST_STRING);
    }

    @Test
    public void setTestBirthday() throws Exception {
        testUser.setBirthday(TEST_DATE);
        assertThat(testUser.getBirthday()).isEqualTo(TEST_DATE);
    }

    @Test
    public void testLastNameField() throws Exception {
        testUser.setLast_name(TEST_STRING);
        assertThat(testUser.getLast_name()).isEqualTo(TEST_STRING);
    }

    @Test
    public void testFirstNameField() throws Exception {
        testUser.setFirst_name(TEST_STRING);
        assertThat(testUser.getFirst_name()).isEqualTo(TEST_STRING);
    }
}
