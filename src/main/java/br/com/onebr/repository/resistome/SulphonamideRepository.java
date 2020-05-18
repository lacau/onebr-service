package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Sulphonamide;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SulphonamideRepository extends JpaRepository<Sulphonamide, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM sulphonamide x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Sulphonamide findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM sulphonamide x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Sulphonamide> findByNameLike(@Param("name") String name);
}
