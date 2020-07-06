package com.envibe.envibe.model;

import com.envibe.envibe.model.validation.constraints.ValidPassword;
import com.envibe.envibe.model.validation.constraints.ValidRole;
import com.envibe.envibe.model.validation.constraints.ValidUsername;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * User model that represents all core properties associated with each user account.
 * @see com.envibe.envibe.dao.UserDao
 *
 * @author ARMmaster17
 */
public class User implements Serializable {
    /**
     * Globally-unique user alias.
     */
    @NotNull
    @NotEmpty
    @ValidUsername
    private String username;

    /**
     * Hash of user password.
     */
    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;

    /**
     * Internal role of user that is consumed internally by the Spring Security framework.
     */
    @NotNull
    @NotEmpty
    @ValidRole
    private String role;

    /**
     * Email address of user.
     */
    @NotNull
    @NotEmpty
    @Email
    private String email;

    /**
     * Country that user currently resides in.
     */
    @NotNull
    @NotEmpty
    private String country;

    /**
     * User's date of birth. Follows the SQL 'YYYY-MM-DD' format.
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * User's last name.
     */
    @NotNull
    @NotEmpty
    private String last_name;

    /**
     * User's first name.
     */
    @NotNull
    @NotEmpty
    private String first_name;

    /**
     * Empty constructor for user model. Consumed by several Thymeleaf templates controlled by {@link com.envibe.envibe.controller.AuthenticationController}.
     */
    public User() {

    }

    // TODO: Update to use Builder model.
    /**
     * Full constructor for user model.
     * @param username Globally-unique user alias.
     * @param password Pre-hashed user password.
     * @param role Internal role of user that is consumed internally by the Spring Security framework.
     * @param email Email address of user.
     * @param country String-serialized country code of country that user currently resides in.
     * @param birthday The user's date of birth in SQL DATE (YYYY-MM-DD)-compatible format.
     * @param last_name The user's last name.
     * @param first_name The user's first name.
     * @deprecated This isn't 2008 anymore. Will be replaced with Builder pattern to be compliant with 2020 coding standards.
     */
    public User(@ValidUsername String username, @ValidPassword String password, @ValidRole String role, @Email String email, @NotEmpty String country, @NotNull Date birthday, @ValidUsername String last_name, @ValidUsername String first_name) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.country = country;
        this.birthday = birthday;
        this.last_name = last_name;
        this.first_name = first_name;
    }

    /**
     * Returns the internal Spring Security formatted user role.
     * @return SSF role.
     */
    public String getRole()
    {
        return this.role;
    }

    /**
     * Sets the user's permission scope inside the Spring Security framework. See the relevant validator classes for valid values.
     * @param role User's assigned role inside the Spring Security framework.
     * @see com.envibe.envibe.model.validation.validator.RoleValidator
     */
    public void setRole(@ValidRole String role) {
        this.role = role;
    }

    /**
     * Returns the globally-unique user alias associated with the account.
     * @return User alias.
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Sets the globally-unique user alias associated with the account. Cannot be changed after record is committed to permanent datastore.
     * @param username User-supplied alias.
     */
    public void setUsername(@Valid @ValidUsername String username) {
        this.username = username;
    }

    /**
     * Gets the pre-hashed password for the user account.
     * @return Pre-hashed user password.
     * @see com.envibe.envibe.service.UserAuthService
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Sets the pre-hashed password for the user account. See the custom authentication framework implementation for accessing the active password encoding class.
     * @param password Pre-hashed user password.
     * @see com.envibe.envibe.service.UserRegistrationService
     */
    public void setPassword(@ValidPassword String password) {
        this.password = password;
    }

    /**
     * Returns the user's email address.
     * @return User's email address.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email address associated with the user account.
     * @param email User's pre-validated email address.
     */
    public void setEmail(@Email String email) {
        this.email = email;
    }

    /**
     * Gets the country that the user currently resides in.
     * @return String-serialized country code as defined by Javascript API on frontend.
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Sets the country that the user currently resides in.
     * @param country String-serialized country code as defined by Javascript API on frontend.
     */
    public void setCountry(@NotEmpty String country) {
        this.country = country;
    }

    /**
     * Returns the user's date of birth in SQL DATE 'YYYY-MM-DD' format.
     * @return User's date of birth.
     */
    public Date getBirthday() {
        return this.birthday;
    }

    /**
     * Sets the user's date of birth.
     * @param birthday User's date of birth that is in a serialized SQL DATE (YYYY-MM-DD)-compatible format.
     */
    public void setBirthday(@NotNull Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns the user's last name.
     * @return The user's last name.
     */
    public String getLast_name() {
        return this.last_name;
    }

    /**
     * Sets the user's last name.
     * @param last_name User's last name.
     */
    public void setLast_name(@NotEmpty String last_name) {
        this.last_name = last_name;
    }

    /**
     * Returns the user's first name.
     * @return The user's first name.
     */
    public String getFirst_name() {
        return this.first_name;
    }

    /**
     * Sets the user's first name.
     * @param first_name The user's first name.
     */
    public void setFirst_name(@NotEmpty String first_name) {
        this.first_name = first_name;
    }
}
