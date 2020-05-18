package br.com.onebr.service;

import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Plasmidome;
import br.com.onebr.repository.PlasmidomeRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class PlasmidomeService {

    @Autowired
    private PlasmidomeRepository plasmidomeRepository;

    public List<Plasmidome> findAll() {
        final List<Plasmidome> plasmidomes = plasmidomeRepository.findAll();

        if (CollectionUtils.isEmpty(plasmidomes)) {
            log.warn("message=No plasmidome found on database.");
            throw new NotFoundApiException("plasmidome.not.found");
        }

        return plasmidomes;
    }
}
