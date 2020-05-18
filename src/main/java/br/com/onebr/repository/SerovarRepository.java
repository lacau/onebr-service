package br.com.onebr.repository;

import br.com.onebr.model.Serovar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerovarRepository extends JpaRepository<Serovar, Long> {

    Serovar findByName(String name);
}
