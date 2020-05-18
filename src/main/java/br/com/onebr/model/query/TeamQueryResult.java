package br.com.onebr.model.query;

public interface TeamQueryResult {

    String QUERY = "SELECT "
        + "t.order as order, "
        + "t.title as title, "
        + "t.name as name, "
        + "t.description as description, "
        + "t.curriculum_link as curriculumLink "
        + "i.id as imageId, "
        + "i.name as imageName "
        + "FROM team t LEFT JOIN image i ON i.id = t.fk_image "
        + "WHERE t.active IS TRUE "
        + "ORDER BY t.order";

    Short getOrder();

    String getTitle();

    String getName();

    String getDescription();

    String getCurriculumLink();

    Long getImageId();

    String getImageName();
}
