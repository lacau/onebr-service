package br.com.onebr.repository;

import br.com.onebr.model.config.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findOneByName(String name);
}
