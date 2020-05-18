package br.com.onebr.repository;

import br.com.onebr.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {

    @Query(value = "SELECT s.* FROM source s "
        + "WHERE LOWER(unaccent(s.name_en)) = LOWER(unaccent(:name)) OR "
        + "LOWER(unaccent(s.name_pt)) = LOWER(unaccent(:name))", nativeQuery = true)
    Source findByName(@Param("name") String name);
}
