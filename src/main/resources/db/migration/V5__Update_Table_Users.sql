ALTER TABLE user_account ADD country varchar(100);
ALTER TABLE user_account ADD birthday DATE;
ALTER TABLE user_account ADD last_name varchar(255);
ALTER TABLE user_account ADD first_name varchar(255);

UPDATE user_account SET country = 'United States', birthday = '2000-01-01', last_name = 'strator', first_name = 'admini' WHERE user_name = 'admin';
UPDATE user_account SET country = 'United States', birthday = '2000-05-27', last_name = 'listnr', first_name = 'avid' WHERE user_name = 'listener';
UPDATE user_account SET country = 'United States', birthday = '2005-11-05', last_name = 'band', first_name = 'bigname' WHERE user_name = 'artist';