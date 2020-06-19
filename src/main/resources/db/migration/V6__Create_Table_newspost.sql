CREATE TABLE IF NOT EXISTS newspost (
    post_id INTEGER PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    post_date DATETIME NOT NULL,
    post_content VARCHAR(255),
    FOREIGN KEY (user_name) REFERENCES user_account(user_name)
);