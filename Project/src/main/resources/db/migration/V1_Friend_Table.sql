CREATE TABLE IF NOT EXISTS user_relationship (
	user_name VARCHAR(255) NOT NULL,
	user_friend VARCHAR(255) NOT NULL,
	FOREIGN KEY (user_name) REFERENCES user_account(user_name)
	FOREIGN KEY (user_friend) REFERENCES user_account(user_name)
);