CREATE TABLE IF NOT EXISTS person (
  ID SERIAL,
  FIRST_NAME varchar(255) NOT NULL,
  LAST_NAME varchar(255) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS bill (
  ID SERIAL,
  PERSON_ID int NOT NULL,
  PRICE int NOT NULL,
  PRIMARY KEY (ID)
);

ALTER TABLE bill
  ADD CONSTRAINT fk_bill_to_person FOREIGN KEY (PERSON_ID) REFERENCES person (ID);
  
CREATE TABLE users(
  username varchar(50) NOT NULL PRIMARY KEY,
  password varchar(50) NOT NULL,
  enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
  username varchar(50) NOT NULL,
  authority varchar(50) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);
  
CREATE SEQUENCE hibernate_sequence