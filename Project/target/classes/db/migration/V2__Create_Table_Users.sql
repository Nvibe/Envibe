-- Script courtesy of https://www.roytuts.com/spring-boot-security-form-based-jdbc-authentication-using-userdetailsservice/

-- Creates a table to store all registered users with their hashed password.
CREATE TABLE IF NOT EXISTS user_account (
    user_name VARCHAR(255) NOT NULL PRIMARY KEY,
    user_password VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL
);

-- Creates a table of roles, describing the privileges of the authenticated user.
CREATE TABLE IF NOT EXISTS user_role (
    user_name VARCHAR(255) NOT NULL,
    user_role VARCHAR(15) NOT NULL,
    FOREIGN KEY (user_name) REFERENCES user_account(user_name)
);