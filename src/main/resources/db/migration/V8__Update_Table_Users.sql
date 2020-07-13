ALTER TABLE user_account ADD image_link varchar(255);

UPDATE user_account SET image_link = 'https://images.homedepot-static.com/productImages/1b29c7df-f11c-46b5-a452-ad988bbba79f/svn/master-lock-padlocks-875dlfhc-64_1000.jpg' WHERE user_name = 'admin';
UPDATE user_account SET image_link = 'https://th.bing.com/th/id/OIP.Iii755-M3cJEpzHCG564SwHaEK?pid=Api&rs=1' WHERE user_name = 'listener';
UPDATE user_account SET image_link = 'https://lh3.googleusercontent.com/Y6SSMwhMgmeALf-TVd0HWXc3y8YkCdgh2UlpLSY9KEw-JgkB3cX4OILjzJ0CEVo5VJIqD7qm=w2247-h1264' WHERE user_name = 'artist';