package br.com.onebr.service;

import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Virulome;
import br.com.onebr.repository.VirulomeRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class VirulomeService {

    @Autowired
    private VirulomeRepository virulomeRepository;

    public List<Virulome> findAll() {
        final List<Virulome> virulomes = virulomeRepository.findAll();

        if (CollectionUtils.isEmpty(virulomes)) {
            log.warn("message=No virulome found on database.");
            throw new NotFoundApiException("virulome.not.found");
        }

        return virulomes;
    }
}
