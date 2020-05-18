package br.com.onebr.repository;

import br.com.onebr.model.AntigenO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntigenORepository extends JpaRepository<AntigenO, Long> {

    AntigenO findByName(String name);
}
