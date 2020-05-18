package br.com.onebr.service;

import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.AntigenH;
import br.com.onebr.model.AntigenO;
import br.com.onebr.repository.AntigenHRepository;
import br.com.onebr.repository.AntigenORepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class SerotypeService {

    @Autowired
    private AntigenORepository antigenORepository;

    @Autowired
    private AntigenHRepository antigenHRepository;

    public List<AntigenO> findAllAntigenO() {
        final List<AntigenO> antigenO = antigenORepository.findAll();

        if (CollectionUtils.isEmpty(antigenO)) {
            log.warn("message=No antigen O found on database.");
            throw new NotFoundApiException("antigen.o.not.found");
        }

        return antigenO;
    }

    public List<AntigenH> findAllAntigenH() {
        final List<AntigenH> antigenH = antigenHRepository.findAll();

        if (CollectionUtils.isEmpty(antigenH)) {
            log.warn("message=No antigen H found on database.");
            throw new NotFoundApiException("antigen.h.not.found");
        }

        return antigenH;
    }
}
