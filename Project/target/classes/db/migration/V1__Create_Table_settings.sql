-- Test that we have permissions to create a table.
CREATE TABLE SETTING (
    KEY varchar(255) NOT NULL PRIMARY KEY,
    VALUE varchar(255) NOT NULL
);

-- Test that we have permission to create records.
INSERT INTO SETTING (KEY, VALUE) VALUES ('testsetting', 'testvalue');

-- Test that we have permission to delete records.
DELETE FROM SETTING WHERE KEY='testsetting';

-- Test that we have permission to drop tables.
DROP TABLE SETTING;