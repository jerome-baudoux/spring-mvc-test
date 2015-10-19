CREATE TABLE person (
  ID int IDENTITY,
  FIRST_NAME varchar(255) NOT NULL,
  LAST_NAME varchar(255) NOT NULL
);

-- CREATE UNIQUE INDEX ix_person_name ON person (FIRST_NAME,LAST_NAME);

CREATE TABLE IF NOT EXISTS bill (
  ID int IDENTITY,
  PERSON_ID int,
  PRICE int,
  CONSTRAINT fk_bill_to_person FOREIGN KEY(PERSON_ID) REFERENCES person(ID)
);


CREATE TABLE users(
  username varchar_ignorecase(50) NOT NULL PRIMARY KEY,
  password varchar_ignorecase(50) NOT NULL,
  enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
  username varchar_ignorecase(50) NOT NULL,
  authority varchar_ignorecase(50) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

-- CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);