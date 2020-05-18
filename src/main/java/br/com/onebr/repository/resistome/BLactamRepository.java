package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.BLactam;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BLactamRepository extends JpaRepository<BLactam, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM b_lactam x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    BLactam findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM b_lactam x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<BLactam> findByNameLike(@Param("name") String name);
}
