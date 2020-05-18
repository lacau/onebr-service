package br.com.onebr.repository;

import br.com.onebr.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query(value = "SELECT r.* FROM region r "
        + "WHERE LOWER(unaccent(r.name_en)) = LOWER(unaccent(:name)) OR "
        + "LOWER(unaccent(r.name_pt)) = LOWER(unaccent(:name))", nativeQuery = true)
    Region findByName(@Param("name") String name);
}
