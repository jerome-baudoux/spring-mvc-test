CREATE TABLE person (
  ID int IDENTITY,
  FIRST_NAME varchar(255) NOT NULL,
  LAST_NAME varchar(255) NOT NULL
);
-- ALTER TABLE person ADD CONSTRAINT PERSON_NAME UNIQUE (FIRST_NAME,LAST_NAME);