package br.com.onebr.repository;

import br.com.onebr.model.HeavyMetal;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HeavyMetalRepository extends JpaRepository<HeavyMetal, Long> {

    @Query("SELECT hm FROM HeavyMetal hm WHERE hm.name IN (:names)")
    List<HeavyMetal> findByNameIn(@Param("names") List<String> names);

    Set<HeavyMetal> findAllByIdIn(List<Long> ids);
}
