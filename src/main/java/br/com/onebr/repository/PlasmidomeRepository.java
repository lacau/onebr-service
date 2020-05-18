package br.com.onebr.repository;

import br.com.onebr.model.Plasmidome;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlasmidomeRepository extends JpaRepository<Plasmidome, Long> {

    @Query("SELECT p FROM Plasmidome p WHERE p.name IN (:names)")
    List<Plasmidome> findByNameIn(@Param("names") List<String> names);

    Set<Plasmidome> findAllByIdIn(List<Long> ids);
}
