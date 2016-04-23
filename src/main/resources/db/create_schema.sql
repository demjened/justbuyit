-- script to generate database schema

CREATE TABLE Company (
  uuid                    VARCHAR(60) NOT NULL,
  name                    VARCHAR(50) NOT NULL,
  phoneNumber             VARCHAR(30) NULL,
  email                   VARCHAR(50) NULL,
  country                 VARCHAR(3)  NULL,
  website                 VARCHAR(50) NULL,
  subscriptionStatus      VARCHAR(20) NULL,
  subscriptionEditionCode VARCHAR(10) NULL,
  CONSTRAINT PK_Company PRIMARY KEY (uuid)
);

CREATE TABLE User (
  uuid          VARCHAR(60)   NOT NULL,
  companyUuid   VARCHAR(60)   NOT NULL,
  openId        VARCHAR(150)  NOT NULL,
  firstName     VARCHAR(30)   NOT NULL,
  lastName      VARCHAR(30)   NOT NULL,
  email         VARCHAR(50)   NULL,
  language      VARCHAR(3)    NULL,
  authenticated SMALLINT      DEFAULT 0 NOT NULL,
  CONSTRAINT PK_User PRIMARY KEY (uuid)
);
