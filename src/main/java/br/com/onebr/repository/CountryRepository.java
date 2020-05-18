package br.com.onebr.repository;

import br.com.onebr.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query(value = "SELECT c.* FROM country c "
        + "WHERE LOWER(unaccent(c.name_en)) = LOWER(unaccent(:name)) OR "
        + "LOWER(unaccent(c.name_pt)) = LOWER(unaccent(:name))", nativeQuery = true)
    Country findByName(@Param("name") String name);
}
