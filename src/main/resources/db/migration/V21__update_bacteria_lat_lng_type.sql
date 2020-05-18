ALTER TABLE bacteria ALTER COLUMN geo_location_lat TYPE float8 USING geo_location_lat::float8;
ALTER TABLE bacteria ALTER COLUMN geo_location_long TYPE float8 USING geo_location_long::float8;