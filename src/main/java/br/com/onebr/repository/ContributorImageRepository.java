package br.com.onebr.repository;

import br.com.onebr.model.config.ContributorImage;
import br.com.onebr.model.query.ContributorImageQueryResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorImageRepository extends JpaRepository<ContributorImage, Long> {

    @Query(name = ContributorImageQueryResult.QUERY, nativeQuery = true)
    List<ContributorImageQueryResult> findAllByOrderByOrder();
}
