package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Phenicol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhenicolRepository extends JpaRepository<Phenicol, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM phenicol x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Phenicol findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM phenicol x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Phenicol> findByNameLike(@Param("name") String name);
}
