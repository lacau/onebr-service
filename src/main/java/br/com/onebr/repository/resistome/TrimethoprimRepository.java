package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Trimethoprim;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrimethoprimRepository extends JpaRepository<Trimethoprim, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM trimethoprim x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Trimethoprim findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM trimethoprim x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Trimethoprim> findByNameLike(@Param("name") String name);
}
