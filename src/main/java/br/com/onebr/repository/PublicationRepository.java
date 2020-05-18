package br.com.onebr.repository;

import br.com.onebr.model.config.Publication;
import br.com.onebr.model.config.PublicationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query(value = "SELECT p.* FROM publication p "
        + "WHERE p.active IS TRUE "
        + "ORDER BY p.date DESC NULLS LAST, p.order", nativeQuery = true)
    Page<Publication> findAllByActiveIsTrueOrderByDateDescNullsLastOrderAsc(Pageable pageable);

    @Query(value = "SELECT p.* FROM publication p "
        + "WHERE p.active IS TRUE AND p.type = :type "
        + "ORDER BY p.date DESC NULLS LAST, p.order", nativeQuery = true)
    Page<Publication> findAllByActiveIsTrueWithTypeOrderByDateDescNullsLastOrderAsc(@Param("type") String type, Pageable pageable);

    @Query(value = "SELECT MAX(p.order) + 1 FROM publication p", nativeQuery = true)
    short getNextOrder();

    @Query(value = "SELECT x.* FROM publication x "
        + "WHERE "
        + ":searchTerm IS NOT NULL AND ( "
        + "     LOWER(unaccent(x.title_pt)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) OR "
        + "     LOWER(unaccent(x.title_en)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) "
        + ") OR :searchTerm IS NULL", nativeQuery = true)
    Page<Publication> findAllByTitleLike(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query(value = "SELECT x.* FROM publication x "
        + "WHERE "
        + "x.fk_user IS NULL OR x.fk_user = :userId AND ( "
        + "     :searchTerm IS NOT NULL AND ("
        + "         LOWER(unaccent(x.title_pt)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) OR "
        + "         LOWER(unaccent(x.title_en)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%'))"
        + "     ) OR :searchTerm IS NULL "
        + ")", nativeQuery = true)
    Page<Publication> findAllByUserIdAndTitleLike(@Param("userId") Long userId, @Param("searchTerm") String searchTerm, Pageable pageable);
}
