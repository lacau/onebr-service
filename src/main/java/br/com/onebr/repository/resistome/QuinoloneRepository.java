package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Quinolone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuinoloneRepository extends JpaRepository<Quinolone, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM quinolone x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Quinolone findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM quinolone x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Quinolone> findByNameLike(@Param("name") String name);
}
