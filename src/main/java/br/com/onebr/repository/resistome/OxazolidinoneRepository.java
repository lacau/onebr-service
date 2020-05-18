package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Oxazolidinone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OxazolidinoneRepository extends JpaRepository<Oxazolidinone, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM oxazolidinone x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Oxazolidinone findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM oxazolidinone x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Oxazolidinone> findByNameLike(@Param("name") String name);
}
