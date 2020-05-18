package br.com.onebr.service;

import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.HeavyMetal;
import br.com.onebr.repository.HeavyMetalRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class HeavyMetalService {

    @Autowired
    private HeavyMetalRepository heavyMetalRepository;

    public List<HeavyMetal> findAll() {
        final List<HeavyMetal> heavyMetals = heavyMetalRepository.findAll();

        if (CollectionUtils.isEmpty(heavyMetals)) {
            log.warn("message=No heavy metal found on database.");
            throw new NotFoundApiException("heavy.metal.typing.not.found");
        }

        return heavyMetals;
    }
}
