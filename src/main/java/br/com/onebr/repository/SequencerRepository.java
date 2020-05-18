package br.com.onebr.repository;

import br.com.onebr.model.Sequencer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequencerRepository extends JpaRepository<Sequencer, Long> {

    Sequencer findByName(String name);
}
