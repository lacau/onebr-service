package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Macrolide;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MacrolideRepository extends JpaRepository<Macrolide, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM macrolide x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Macrolide findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM macrolide x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Macrolide> findByNameLike(@Param("name") String name);
}
