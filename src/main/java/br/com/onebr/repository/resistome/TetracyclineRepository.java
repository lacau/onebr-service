package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Tetracycline;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TetracyclineRepository extends JpaRepository<Tetracycline, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM tetracycline x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Tetracycline findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM tetracycline x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Tetracycline> findByNameLike(@Param("name") String name);
}
