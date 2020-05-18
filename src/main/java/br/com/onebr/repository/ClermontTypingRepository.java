package br.com.onebr.repository;

import br.com.onebr.model.ClermontTyping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClermontTypingRepository extends JpaRepository<ClermontTyping, Long> {

    ClermontTyping findByName(String name);
}
