package br.com.onebr.repository;

import br.com.onebr.model.Virulome;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VirulomeRepository extends JpaRepository<Virulome, Long> {

    @Query("SELECT v FROM Virulome v WHERE v.name IN (:names)")
    List<Virulome> findByNameIn(@Param("names") List<String> names);

    Virulome findOneByName(String name);

    Set<Virulome> findAllByIdIn(List<Long> ids);
}
