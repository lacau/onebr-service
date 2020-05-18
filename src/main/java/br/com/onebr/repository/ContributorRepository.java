package br.com.onebr.repository;

import br.com.onebr.model.config.Contributor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    List<Contributor> findAllByActiveIsTrueOrderByOrder();
}
