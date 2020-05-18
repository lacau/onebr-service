CREATE TABLE specie (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(255) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_specie;
ALTER TABLE specie ALTER COLUMN id SET DEFAULT nextval('seq_specie');

CREATE TABLE user_specie (
  fk_user BIGINT NOT NULL,
  fk_specie BIGINT NOT NULL,
  PRIMARY KEY (fk_user, fk_specie)
)
WITH (oids = false);

ALTER TABLE user_specie ADD CONSTRAINT fk_user_specie_user FOREIGN KEY (fk_user) REFERENCES "user" (id);
ALTER TABLE user_specie ADD CONSTRAINT fk_user_specie_specie FOREIGN KEY (fk_specie) REFERENCES specie (id);

CREATE TABLE region (
  id BIGINT PRIMARY KEY NOT NULL,
  name_en VARCHAR(45) NOT NULL,
  name_pt VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_region;
ALTER TABLE region ALTER COLUMN id SET DEFAULT nextval('seq_region');

CREATE TABLE origin (
  id BIGINT PRIMARY KEY NOT NULL,
  name_en VARCHAR(45) NOT NULL,
  name_pt VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_origin;
ALTER TABLE origin ALTER COLUMN id SET DEFAULT nextval('seq_origin');

CREATE TABLE source (
  id BIGINT PRIMARY KEY NOT NULL,
  name_en VARCHAR(45) NOT NULL,
  name_pt VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_source;
ALTER TABLE source ALTER COLUMN id SET DEFAULT nextval('seq_source');

CREATE TABLE resistome (
  id BIGINT PRIMARY KEY  NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_resistome;
ALTER TABLE resistome ALTER COLUMN id SET DEFAULT nextval('seq_resistome');

CREATE TABLE country (
  id BIGINT PRIMARY KEY  NOT NULL,
  name_en VARCHAR(45) NOT NULL,
  name_pt VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_country;
ALTER TABLE country ALTER COLUMN id SET DEFAULT nextval('seq_country');

CREATE TABLE city (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL,
  fk_country BIGINT NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_city;
ALTER TABLE city ALTER COLUMN id SET DEFAULT nextval('seq_city');
ALTER TABLE city ADD CONSTRAINT fk_city_country FOREIGN KEY (fk_country) REFERENCES country (id);

CREATE TABLE antigen_o (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_antigen_o;
ALTER TABLE antigen_o ALTER COLUMN id SET DEFAULT nextval('seq_antigen_o');

CREATE TABLE antigen_h (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_antigen_h;
ALTER TABLE antigen_h ALTER COLUMN id SET DEFAULT nextval('seq_antigen_h');

CREATE TABLE serotype (
  id BIGINT PRIMARY KEY NOT NULL,
  fk_antigen_o BIGINT,
  fk_antigen_h BIGINT
)
WITH (oids = false);

CREATE SEQUENCE seq_serotype;
ALTER TABLE serotype ALTER COLUMN id SET DEFAULT nextval('seq_serotype');
ALTER TABLE serotype ADD CONSTRAINT fk_serotype_antigen_o FOREIGN KEY (fk_antigen_o) REFERENCES antigen_o (id);
ALTER TABLE serotype ADD CONSTRAINT fk_serotype_antigen_h FOREIGN KEY (fk_antigen_h) REFERENCES antigen_h (id);

CREATE TABLE serovar (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_serovar;
ALTER TABLE serovar ALTER COLUMN id SET DEFAULT nextval('seq_serovar');

CREATE TABLE clermont_typing (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_clermont_typing;
ALTER TABLE clermont_typing ALTER COLUMN id SET DEFAULT nextval('seq_clermont_typing');

CREATE TABLE sequencer (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_sequencer;
ALTER TABLE sequencer ALTER COLUMN id SET DEFAULT nextval('seq_sequencer');

CREATE TABLE antibiogram (
  id BIGINT PRIMARY KEY NOT NULL,
  mer VARCHAR(1) NOT NULL,
  etp VARCHAR(1) NOT NULL,
  ipm VARCHAR(1) NOT NULL,
  cro VARCHAR(1) NOT NULL,
  caz VARCHAR(1) NOT NULL,
  cfx VARCHAR(1) NOT NULL,
  cpm VARCHAR(1) NOT NULL,
  ctx VARCHAR(1) NOT NULL,
  nal VARCHAR(1) NOT NULL,
  cip VARCHAR(1) NOT NULL,
  amc VARCHAR(1) NOT NULL,
  atm VARCHAR(1) NOT NULL,
  ami VARCHAR(1) NOT NULL,
  gen VARCHAR(1) NOT NULL,
  sxt VARCHAR(1) NOT NULL,
  eno VARCHAR(1) NOT NULL,
  chl VARCHAR(1) NOT NULL,
  fos VARCHAR(1) NOT NULL,
  cep VARCHAR(1) NOT NULL,
  ctf VARCHAR(1) NOT NULL,
  amp VARCHAR(1) NOT NULL,
  tet VARCHAR(1) NOT NULL,
  col VARCHAR(1) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_antibiogram;
ALTER TABLE antibiogram ALTER COLUMN id SET DEFAULT nextval('seq_antibiogram');

CREATE TABLE bacteria (
  id BIGINT PRIMARY KEY NOT NULL,
  barcode VARCHAR(45) NOT NULL,
  identification VARCHAR(45) NOT NULL,
  researcher_name VARCHAR(45) NOT NULL,
  fk_specie BIGINT NOT NULL,
  fk_region BIGINT,
  fk_city BIGINT,
  geo_location_lat FLOAT4,
  geo_location_long FLOAT4,
  date DATE,
  fk_origin BIGINT,
  fk_source BIGINT,
  host VARCHAR(255) NOT NULL,
  st VARCHAR(45) NOT NULL,
  fk_resistome BIGINT NOT NULL,
  k_locus VARCHAR(45) NOT NULL,
  wzi VARCHAR(45) NOT NULL,
  wzc VARCHAR(45) NOT NULL,
  fim_type VARCHAR(45) NOT NULL,
  fk_clermont_typing BIGINT NOT NULL,
  fk_serotype BIGINT,
  fk_serovar BIGINT NOT NULL,
  fk_antibiogram BIGINT NOT NULL,
  fk_sequencer BIGINT,
  sequencing_date DATE,
  assembler VARCHAR(45) NOT NULL,
  date_of_assembly DATE,
  genome_bp INT,
  contigs_no SMALLINT,
  access_no_gb VARCHAR(255),
  paper_published VARCHAR(255),
  valid_month BOOLEAN NOT NULL DEFAULT false
)
WITH (oids = false);

CREATE SEQUENCE seq_bacteria;
ALTER TABLE bacteria ALTER COLUMN id SET DEFAULT nextval('seq_bacteria');
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_specie FOREIGN KEY (fk_specie) REFERENCES specie (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_region FOREIGN KEY (fk_region) REFERENCES region (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_city FOREIGN KEY (fk_city) REFERENCES city (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_origin FOREIGN KEY (fk_origin) REFERENCES origin (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_source FOREIGN KEY (fk_source) REFERENCES source (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_clermont_typing FOREIGN KEY (fk_clermont_typing) REFERENCES clermont_typing (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_serotype FOREIGN KEY (fk_serotype) REFERENCES serotype (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_serovar FOREIGN KEY (fk_serovar) REFERENCES serovar (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_antibiogram FOREIGN KEY (fk_antibiogram) REFERENCES antibiogram (id);
ALTER TABLE bacteria ADD CONSTRAINT fk_bacteria_sequencer FOREIGN KEY (fk_sequencer) REFERENCES sequencer (id);

CREATE TABLE plasmidome (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_plasmidome;
ALTER TABLE plasmidome ALTER COLUMN id SET DEFAULT nextval('seq_plasmidome');

CREATE TABLE plasmidome_bacteria (
  fk_bacteria BIGINT NOT NULL,
  fk_plasmidome BIGINT NOT NULL,
  PRIMARY KEY (fk_bacteria, fk_plasmidome)
)
WITH (oids = false);

ALTER TABLE plasmidome_bacteria ADD CONSTRAINT fk_plasmidome_bacteria_bacteria FOREIGN KEY (fk_bacteria) REFERENCES bacteria (id);
ALTER TABLE plasmidome_bacteria ADD CONSTRAINT fk_plasmidome_bacteria_plasmidome FOREIGN KEY (fk_plasmidome) REFERENCES plasmidome (id);

CREATE TABLE virulome (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_virulome;
ALTER TABLE virulome ALTER COLUMN id SET DEFAULT nextval('seq_virulome');

CREATE TABLE virulome_bacteria (
  fk_bacteria BIGINT NOT NULL,
  fk_virulome BIGINT NOT NULL,
  PRIMARY KEY (fk_bacteria, fk_virulome)
)
WITH (oids = false);

ALTER TABLE virulome_bacteria ADD CONSTRAINT fk_virulome_bacteria_bacteria FOREIGN KEY (fk_bacteria) REFERENCES bacteria (id);
ALTER TABLE virulome_bacteria ADD CONSTRAINT fk_virulome_bacteria_virulome FOREIGN KEY (fk_virulome) REFERENCES virulome (id);

CREATE TABLE heavy_metal (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

CREATE SEQUENCE seq_heavy_metal;
ALTER TABLE heavy_metal ALTER COLUMN id SET DEFAULT nextval('seq_heavy_metal');

CREATE TABLE heavy_metal_bacteria (
  fk_bacteria BIGINT NOT NULL,
  fk_heavy_metal BIGINT NOT NULL,
  PRIMARY KEY (fk_bacteria, fk_heavy_metal)
)
WITH (oids = false);

ALTER TABLE heavy_metal_bacteria ADD CONSTRAINT fk_heavy_metal_bacteria_bacteria FOREIGN KEY (fk_bacteria) REFERENCES bacteria (id);
ALTER TABLE heavy_metal_bacteria ADD CONSTRAINT fk_heavy_metal_bacteria_virulome FOREIGN KEY (fk_heavy_metal) REFERENCES heavy_metal (id);
