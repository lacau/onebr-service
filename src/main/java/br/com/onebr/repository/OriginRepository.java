package br.com.onebr.repository;

import br.com.onebr.model.Origin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginRepository extends JpaRepository<Origin, Long> {

    @Query(value = "SELECT o.* FROM origin o "
        + "WHERE LOWER(unaccent(o.name_en)) = LOWER(unaccent(:name)) OR "
        + "LOWER(unaccent(o.name_pt)) = LOWER(unaccent(:name))", nativeQuery = true)
    Origin findByName(@Param("name") String name);
}
