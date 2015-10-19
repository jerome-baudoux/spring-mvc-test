CREATE TABLE IF NOT EXISTS `person` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` varchar(255) NOT NULL,
  `LAST_NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PERSON_NAME` (`FIRST_NAME`,`LAST_NAME`)
) AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `bill` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PERSON_ID` int(11) NOT NULL,
  `PRICE` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `PERSON_ID` (`PERSON_ID`)
) AUTO_INCREMENT=1;

ALTER TABLE `bill`
  ADD CONSTRAINT `fk_bill_to_person` FOREIGN KEY (`PERSON_ID`) REFERENCES `person` (`ID`);
  

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
  