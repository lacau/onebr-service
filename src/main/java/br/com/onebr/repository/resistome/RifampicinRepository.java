package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Rifampicin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RifampicinRepository extends JpaRepository<Rifampicin, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM rifampicin x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Rifampicin findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM rifampicin x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Rifampicin> findByNameLike(@Param("name") String name);
}
