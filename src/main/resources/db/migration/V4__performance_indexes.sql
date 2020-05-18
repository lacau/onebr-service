CREATE INDEX user_username_idx ON public."user" (username);
CREATE INDEX user_role_fk_role_idx ON public.user_role (fk_role);
CREATE INDEX publication_date_idx ON public.publication ("date");
CREATE INDEX bacteria_fk_specie_idx ON public.bacteria (fk_specie);
CREATE INDEX bacteria_fk_resistome_idx ON public.bacteria USING btree (fk_resistome);
CREATE INDEX bacteria_fk_city_idx ON public.bacteria (fk_city);
CREATE INDEX bacteria_fk_origin_idx ON public.bacteria (fk_origin);
CREATE INDEX bacteria_fk_source_idx ON public.bacteria (fk_source);

-- unique
CREATE UNIQUE INDEX view_data_key_scope_admin_idx ON public.view_data ("key","scope","admin");
CREATE UNIQUE INDEX bacteria_barcode_id_idx ON public.bacteria (barcode, identification);
CREATE UNIQUE INDEX plasmidome_name_idx ON public.plasmidome ("name");
CREATE UNIQUE INDEX virulome_name_idx ON public.virulome ("name");
CREATE UNIQUE INDEX specie_name_idx ON public.specie ("name");
CREATE UNIQUE INDEX origin_name_idx ON public.origin (name_en,name_pt);
CREATE UNIQUE INDEX source_name_idx ON public.source (name_en,name_pt);
CREATE UNIQUE INDEX clermont_typing_name_idx ON public.clermont_typing (name);
CREATE UNIQUE INDEX antigen_o_name_idx ON public.antigen_o (name);
CREATE UNIQUE INDEX antigen_h_name_idx ON public.antigen_h (name);
CREATE UNIQUE INDEX serovar_name_idx ON public.serovar (name);
CREATE UNIQUE INDEX sequencer_name_idx ON public.sequencer (name);
CREATE UNIQUE INDEX heavy_metal_name_idx ON public.heavy_metal (name);
CREATE UNIQUE INDEX b_lactam_name_idx ON public.b_lactam (name);
CREATE UNIQUE INDEX phenicol_name_idx ON public.phenicol (name);
CREATE UNIQUE INDEX colistin_name_idx ON public.colistin (name);
CREATE UNIQUE INDEX tetracycline_name_idx ON public.tetracycline (name);
CREATE UNIQUE INDEX glycopeptide_name_idx ON public.glycopeptide (name);
CREATE UNIQUE INDEX aminoglycoside_name_idx ON public.aminoglycoside (name);
CREATE UNIQUE INDEX fosfomycin_name_idx ON public.fosfomycin (name);
CREATE UNIQUE INDEX trimethoprim_name_idx ON public.trimethoprim (name);
CREATE UNIQUE INDEX nitroimidazole_name_idx ON public.nitroimidazole (name);
CREATE UNIQUE INDEX macrolide_name_idx ON public.macrolide (name);
CREATE UNIQUE INDEX quinolone_name_idx ON public.quinolone (name);
CREATE UNIQUE INDEX sulphonamide_name_idx ON public.sulphonamide (name);
CREATE UNIQUE INDEX rifampicin_name_idx ON public.rifampicin (name);
CREATE UNIQUE INDEX fusidic_acid_name_idx ON public.fusidic_acid (name);
CREATE UNIQUE INDEX oxazolidinone_name_idx ON public.oxazolidinone (name);

CREATE UNIQUE INDEX team_order_idx ON public.team ("order");
CREATE UNIQUE INDEX contributor_order_idx ON public.contributor ("order");

CREATE UNIQUE INDEX support_order_idx ON public.support ("order");
CREATE UNIQUE INDEX support_fk_image_idx ON public.support (fk_image);

CREATE UNIQUE INDEX contributor_image_order_idx ON public.contributor_image ("order");
CREATE UNIQUE INDEX contributor_image_fk_image_idx ON public.contributor_image (fk_image);

CREATE UNIQUE INDEX dashboard_menu_order_idx ON public.dashboard_menu ("order");
CREATE UNIQUE INDEX dashboard_options_order_idx ON public.dashboard_options ("order");