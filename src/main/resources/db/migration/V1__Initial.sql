-- Execute as superuser
-- CREATE EXTENSION unaccent;

CREATE TABLE image (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(255) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_image;
ALTER TABLE image ALTER COLUMN id SET DEFAULT nextval('seq_image');

CREATE TABLE support (
  id BIGINT PRIMARY KEY NOT NULL,
  "order" INT2 NOT NULL,
  name VARCHAR(255),
  fk_image BIGINT,
  active BOOLEAN NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_support;
ALTER TABLE support ALTER COLUMN id SET DEFAULT nextval('seq_support');
ALTER TABLE support ADD CONSTRAINT fk_support_image FOREIGN KEY (fk_image) REFERENCES image (id);

CREATE TABLE profile (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  fk_image BIGINT
)
WITH (oids = false);

CREATE SEQUENCE seq_profile;
ALTER TABLE profile ALTER COLUMN id SET DEFAULT nextval('seq_profile');
ALTER TABLE profile ADD CONSTRAINT fk_profile_image FOREIGN KEY (fk_image) REFERENCES image (id);

CREATE TABLE "user" (
  id BIGINT PRIMARY KEY NOT NULL,
  fk_profile BIGINT NOT NULL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  active BOOLEAN NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_user;
ALTER TABLE "user" ALTER COLUMN id SET DEFAULT nextval('seq_user');
ALTER TABLE "user" ADD CONSTRAINT fk_user_profile FOREIGN KEY (fk_profile) REFERENCES profile (id);

CREATE TABLE role (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_role;
ALTER TABLE role ALTER COLUMN id SET DEFAULT nextval('seq_role');

CREATE TABLE user_role (
  fk_user BIGINT NOT NULL,
  fk_role BIGINT NOT NULL,
  PRIMARY KEY (fk_user, fk_role)
)
WITH (oids = false);

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_user FOREIGN KEY (fk_user) REFERENCES "user" (id);
ALTER TABLE user_role ADD CONSTRAINT fk_user_role_role FOREIGN KEY (fk_role) REFERENCES role (id);

CREATE TABLE team (
  id BIGINT PRIMARY KEY NOT NULL,
  "order" INT2 NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NULL,
  title VARCHAR(45) NOT NULL,
  curriculum_link VARCHAR(255) NOT NULL,
  fk_image BIGINT,
  active BOOLEAN NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_team;
ALTER TABLE team ALTER COLUMN id SET DEFAULT nextval('seq_team');
ALTER TABLE team ADD CONSTRAINT fk_team_image FOREIGN KEY (fk_image) REFERENCES image (id);

CREATE TABLE publication (
  id BIGINT PRIMARY KEY NOT NULL,
  type VARCHAR(45) NOT NULL,
  "order" INT2,
  title_en VARCHAR(1024) NULL,
  title_pt VARCHAR(1024) NULL,
  description_en VARCHAR(255) NULL,
  description_pt VARCHAR(255) NULL,
  link VARCHAR(255) NULL,
  "date" TIMESTAMP,
  fk_user BIGINT,
  active BOOLEAN NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_publication;
ALTER TABLE publication ALTER COLUMN id SET DEFAULT nextval('seq_publication');
ALTER TABLE publication ADD CONSTRAINT fk_publication_user FOREIGN KEY (fk_user) REFERENCES "user" (id);

CREATE TABLE contributor (
  id BIGINT PRIMARY KEY NOT NULL,
  "order" INT2 NOT NULL,
  name VARCHAR(45) NOT NULL,
  description VARCHAR(255) NOT NULL,
  active BOOLEAN NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_contributor;
ALTER TABLE contributor ALTER COLUMN id SET DEFAULT nextval('seq_contributor');

CREATE TABLE contributor_image (
  id BIGINT PRIMARY KEY NOT NULL,
  "order" INT2 NOT NULL,
  name VARCHAR(255),
  fk_image BIGINT NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_contributor_image;
ALTER TABLE contributor_image ALTER COLUMN id SET DEFAULT nextval('seq_contributor_image');
ALTER TABLE contributor_image ADD CONSTRAINT fk_contributor_image_image FOREIGN KEY (fk_image) REFERENCES image (id);

CREATE TABLE view_data (
  id BIGINT PRIMARY KEY NOT NULL,
  "key" VARCHAR(255) NOT NULL,
  "scope" VARCHAR(45) NOT NULL,
  content_en TEXT NULL,
  content_pt TEXT NULL,
  admin BOOLEAN NOT NULL DEFAULT false
)
WITH (oids = false);
CREATE SEQUENCE seq_view_data;
ALTER TABLE view_data ALTER COLUMN id SET DEFAULT nextval('seq_view_data');

CREATE TABLE dashboard_menu (
  id BIGINT PRIMARY KEY NOT NULL,
  "order" INT2 NOT NULL,
  "key" VARCHAR(45) NOT NULL,
  name_en VARCHAR(45) NOT NULL,
  name_pt VARCHAR(45),
  admin BOOLEAN NOT NULL DEFAULT false,
  active BOOLEAN NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_dashboard_menu;
ALTER TABLE dashboard_menu ALTER COLUMN id SET DEFAULT nextval('seq_dashboard_menu');

CREATE TABLE dashboard_options (
  id BIGINT PRIMARY KEY NOT NULL,
  "order" INT2 NOT NULL,
  "key" VARCHAR(45) NOT NULL,
  name_en VARCHAR(45) NOT NULL,
  name_pt VARCHAR(45),
  admin BOOLEAN NOT NULL DEFAULT false,
  active BOOLEAN NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_dashboard_options;
ALTER TABLE dashboard_options ALTER COLUMN id SET DEFAULT nextval('seq_dashboard_options');