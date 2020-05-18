package br.com.onebr.repository.resistome;

import br.com.onebr.model.resistome.FusidicAcid;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FusidicAcidRepository extends JpaRepository<FusidicAcid, Long>, ResistomeBaseRepository {

    @Query(value = "SELECT x.* FROM fusidic_acid x WHERE LOWER(x.name) = LOWER(:name)", nativeQuery = true)
    FusidicAcid findByName(@Param("name") String name);

    @Query(value = "SELECT x.* FROM fusidic_acid x WHERE LOWER(x.name) LIKE LOWER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<FusidicAcid> findByNameLike(@Param("name") String name);
}
