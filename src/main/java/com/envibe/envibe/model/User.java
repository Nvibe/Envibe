package com.envibe.envibe.model;

import com.envibe.envibe.model.validation.constraints.ValidPassword;
import com.envibe.envibe.model.validation.constraints.ValidRole;
import com.envibe.envibe.model.validation.constraints.ValidUsername;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
     * @deprecated This isn't 2008 anymore. Will be replaced with Builder pattern to be compliant with 2020 coding standards.
     */
    public User(@ValidUsername String username, @ValidPassword String password, @ValidRole String role, @Email String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
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
    public void setUsername(@ValidUsername String username) {
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
}
