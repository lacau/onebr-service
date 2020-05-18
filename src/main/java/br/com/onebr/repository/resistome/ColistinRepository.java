package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Colistin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColistinRepository extends JpaRepository<Colistin, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM colistin x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Colistin findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM colistin x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Colistin> findByNameLike(@Param("name") String name);
}
