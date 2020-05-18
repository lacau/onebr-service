package br.com.onebr.service;

import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Serovar;
import br.com.onebr.repository.SerovarRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class SerovarService {

    @Autowired
    private SerovarRepository serovarRepository;

    public List<Serovar> findAll() {
        final List<Serovar> serovars = serovarRepository.findAll();

        if (CollectionUtils.isEmpty(serovars)) {
            log.warn("message=No serovar found on database.");
            throw new NotFoundApiException("serovar.not.found");
        }

        return serovars;
    }
}
