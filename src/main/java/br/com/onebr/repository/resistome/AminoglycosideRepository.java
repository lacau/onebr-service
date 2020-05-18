package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Aminoglycoside;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AminoglycosideRepository extends JpaRepository<Aminoglycoside, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM aminoglycoside x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Aminoglycoside findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM aminoglycoside x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Aminoglycoside> findByNameLike(@Param("name") String name);
}
