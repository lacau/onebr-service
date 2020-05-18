package br.com.onebr.repository;

import br.com.onebr.model.config.Support;
import br.com.onebr.model.query.SupportQueryResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {

    @Query(name = SupportQueryResult.QUERY, nativeQuery = true)
    List<SupportQueryResult> findAllByActiveIsTrueOrderByOrder();
}
