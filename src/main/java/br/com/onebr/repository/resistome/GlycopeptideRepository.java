package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.Glycopeptide;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlycopeptideRepository extends JpaRepository<Glycopeptide, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM glycopeptide x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    Glycopeptide findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM glycopeptide x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Glycopeptide> findByNameLike(@Param("name") String name);
}
