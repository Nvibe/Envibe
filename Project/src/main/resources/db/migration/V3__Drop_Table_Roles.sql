ALTER TABLE user_account ADD user_role varchar(15) NOT NULL; -- Required by Spring Security framework.
DROP TABLE user_role;