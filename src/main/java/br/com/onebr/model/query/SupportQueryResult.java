package br.com.onebr.model.query;

public interface SupportQueryResult {

    String QUERY = "SELECT "
        + "s.order as order, "
        + "s.name as name, "
        + "i.id as imageId, "
        + "i.name as imageName "
        + "FROM support s LEFT JOIN image i ON i.id = s.fk_image "
        + "ORDER BY t.order";

    Short getOrder();

    String getName();

    Long getImageId();

    String getImageName();
}
