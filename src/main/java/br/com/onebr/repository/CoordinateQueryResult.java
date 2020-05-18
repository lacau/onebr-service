package br.com.onebr.repository;

public interface CoordinateQueryResult {

    String QUERY = "SELECT "
        + "id as id, "
        + "geo_location_lat as latitude, "
        + "geo_location_long as longitude "
        + "FROM bacteria "
        + "WHERE fk_specie =:specieId "
        + "AND geo_location_lat IS NOT NULL "
        + "AND geo_location_long IS NOT NULL";

    Long getId();

    float getLatitude();

    float getLongitude();
}
