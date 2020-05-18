package br.com.onebr.repository;

import br.com.onebr.model.config.Team;
import br.com.onebr.model.query.TeamQueryResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(name = TeamQueryResult.QUERY, nativeQuery = true)
    List<TeamQueryResult> findAllByActiveIsTrueOrderByOrder();
}
