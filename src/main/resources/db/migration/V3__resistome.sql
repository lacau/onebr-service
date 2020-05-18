CREATE SEQUENCE seq_resistome_att;

CREATE TABLE b_lactam (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE b_lactam ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE b_lactam_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_b_lactam BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_b_lactam)
)
WITH (oids = false);

ALTER TABLE b_lactam_resistome ADD CONSTRAINT fk_b_lactam_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE b_lactam_resistome ADD CONSTRAINT fk_b_lactam_resistome_b_lactam FOREIGN KEY (fk_b_lactam) REFERENCES b_lactam (id);

CREATE TABLE phenicol (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE phenicol ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE phenicol_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_phenicol BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_phenicol)
)
WITH (oids = false);

ALTER TABLE phenicol_resistome ADD CONSTRAINT fk_phenicol_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE phenicol_resistome ADD CONSTRAINT fk_phenicol_resistome_phenicol FOREIGN KEY (fk_phenicol) REFERENCES phenicol (id);

CREATE TABLE colistin (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE colistin ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE colistin_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_colistin BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_colistin)
)
WITH (oids = false);

ALTER TABLE colistin_resistome ADD CONSTRAINT fk_colistin_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE colistin_resistome ADD CONSTRAINT fk_colistin_resistome_colistin FOREIGN KEY (fk_colistin) REFERENCES colistin (id);

CREATE TABLE tetracycline (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE tetracycline ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE tetracycline_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_tetracycline BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_tetracycline)
)
WITH (oids = false);

ALTER TABLE tetracycline_resistome ADD CONSTRAINT fk_tetracycline_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE tetracycline_resistome ADD CONSTRAINT fk_tetracycline_resistome_tetracycline FOREIGN KEY (fk_tetracycline) REFERENCES tetracycline (id);

CREATE TABLE glycopeptide (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE glycopeptide ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE glycopeptide_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_glycopeptide BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_glycopeptide)
)
WITH (oids = false);

ALTER TABLE glycopeptide_resistome ADD CONSTRAINT fk_glycopeptide_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE glycopeptide_resistome ADD CONSTRAINT fk_glycopeptide_resistome_glycopeptide FOREIGN KEY (fk_glycopeptide) REFERENCES glycopeptide (id);

CREATE TABLE aminoglycoside (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE aminoglycoside ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE aminoglycoside_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_aminoglycoside BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_aminoglycoside)
)
WITH (oids = false);

ALTER TABLE aminoglycoside_resistome ADD CONSTRAINT fk_aminoglycoside_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE aminoglycoside_resistome ADD CONSTRAINT fk_aminoglycoside_resistome_aminoglycoside FOREIGN KEY (fk_aminoglycoside) REFERENCES aminoglycoside (id);

CREATE TABLE fosfomycin (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE fosfomycin ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE fosfomycin_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_fosfomycin BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_fosfomycin)
)
WITH (oids = false);

ALTER TABLE fosfomycin_resistome ADD CONSTRAINT fk_fosfomycin_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE fosfomycin_resistome ADD CONSTRAINT fk_fosfomycin_resistome_fosfomycin FOREIGN KEY (fk_fosfomycin) REFERENCES fosfomycin (id);

CREATE TABLE trimethoprim (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE trimethoprim ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE trimethoprim_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_trimethoprim BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_trimethoprim)
)
WITH (oids = false);

ALTER TABLE trimethoprim_resistome ADD CONSTRAINT fk_trimethoprim_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE trimethoprim_resistome ADD CONSTRAINT fk_trimethoprim_resistome_trimethoprim FOREIGN KEY (fk_trimethoprim) REFERENCES trimethoprim (id);

CREATE TABLE macrolide (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE macrolide ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE macrolide_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_macrolide BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_macrolide)
)
WITH (oids = false);

ALTER TABLE macrolide_resistome ADD CONSTRAINT fk_macrolide_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE macrolide_resistome ADD CONSTRAINT fk_macrolide_resistome_macrolide FOREIGN KEY (fk_macrolide) REFERENCES macrolide (id);

CREATE TABLE nitroimidazole (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE nitroimidazole ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE nitroimidazole_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_nitroimidazole BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_nitroimidazole)
)
WITH (oids = false);

ALTER TABLE nitroimidazole_resistome ADD CONSTRAINT fk_nitroimidazole_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE nitroimidazole_resistome ADD CONSTRAINT fk_nitroimidazole_resistome_nitroimidazole FOREIGN KEY (fk_nitroimidazole) REFERENCES nitroimidazole (id);

CREATE TABLE quinolone (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE quinolone ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE quinolone_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_quinolone BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_quinolone)
)
WITH (oids = false);

ALTER TABLE quinolone_resistome ADD CONSTRAINT fk_quinolone_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE quinolone_resistome ADD CONSTRAINT fk_quinolone_resistome_quinolone FOREIGN KEY (fk_quinolone) REFERENCES quinolone (id);

CREATE TABLE sulphonamide (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE sulphonamide ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE sulphonamide_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_sulphonamide BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_sulphonamide)
)
WITH (oids = false);

ALTER TABLE sulphonamide_resistome ADD CONSTRAINT fk_sulphonamide_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE sulphonamide_resistome ADD CONSTRAINT fk_sulphonamide_resistome_sulphonamide FOREIGN KEY (fk_sulphonamide) REFERENCES sulphonamide (id);

CREATE TABLE rifampicin (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE rifampicin ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE rifampicin_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_rifampicin BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_rifampicin)
)
WITH (oids = false);

ALTER TABLE rifampicin_resistome ADD CONSTRAINT fk_rifampicin_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE rifampicin_resistome ADD CONSTRAINT fk_rifampicin_resistome_rifampicin FOREIGN KEY (fk_rifampicin) REFERENCES rifampicin (id);

CREATE TABLE fusidic_acid (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE fusidic_acid ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE fusidic_acid_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_fusidic_acid BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_fusidic_acid)
)
WITH (oids = false);

ALTER TABLE fusidic_acid_resistome ADD CONSTRAINT fk_fusidic_acid_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE fusidic_acid_resistome ADD CONSTRAINT fk_fusidic_acid_resistome_fusidic_acid FOREIGN KEY (fk_fusidic_acid) REFERENCES fusidic_acid (id);

CREATE TABLE oxazolidinone (
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(45) NOT NULL
)
WITH (oids = false);

ALTER TABLE oxazolidinone ALTER COLUMN id SET DEFAULT nextval('seq_resistome_att');

CREATE TABLE oxazolidinone_resistome (
  fk_resistome BIGINT NOT NULL,
  fk_oxazolidinone BIGINT NOT NULL,
  PRIMARY KEY (fk_resistome, fk_oxazolidinone)
)
WITH (oids = false);

ALTER TABLE oxazolidinone_resistome ADD CONSTRAINT fk_oxazolidinone_resistome_resistome FOREIGN KEY (fk_resistome) REFERENCES resistome (id);
ALTER TABLE oxazolidinone_resistome ADD CONSTRAINT fk_oxazolidinone_resistome_oxazolidinone FOREIGN KEY (fk_oxazolidinone) REFERENCES oxazolidinone (id);
