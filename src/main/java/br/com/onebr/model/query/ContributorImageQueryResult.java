package br.com.onebr.model.query;

public interface ContributorImageQueryResult {

    String QUERY = "SELECT "
        + "c.order as order, "
        + "c.name as name, "
        + "i.id as imageId, "
        + "i.name as imageName "
        + "FROM contributor_image c LEFT JOIN image i ON i.id = c.fk_image "
        + "ORDER BY t.order";

    Short getOrder();

    String getName();

    Long getImageId();

    String getImageName();
}
