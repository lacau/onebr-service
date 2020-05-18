package br.com.onebr.repository;

import br.com.onebr.model.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query(value = "SELECT c.* FROM city c WHERE LOWER(unaccent(c.name)) = LOWER(unaccent(:name))", nativeQuery = true)
    City findByName(@Param("name") String name);

    @Query(value = "SELECT c.* FROM city c WHERE LOWER(unaccent(c.name)) LIKE LOWER(CONCAT('%',unaccent(:name),'%')) AND c.fk_country =:countryId", nativeQuery = true)
    List<City> findByNameLikeAndCountryId(@Param("name") String name, @Param("countryId") Long countryId);
}
