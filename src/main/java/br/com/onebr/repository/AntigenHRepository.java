package br.com.onebr.repository;

import br.com.onebr.model.AntigenH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntigenHRepository extends JpaRepository<AntigenH, Long> {

    AntigenH findByName(String name);
}
