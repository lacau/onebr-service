package br.com.onebr.service;

import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Sequencer;
import br.com.onebr.repository.SequencerRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class SequencerService {

    @Autowired
    private SequencerRepository sequencerRepository;

    public List<Sequencer> findAll() {
        final List<Sequencer> sequencers = sequencerRepository.findAll();

        if (CollectionUtils.isEmpty(sequencers)) {
            log.warn("message=No sequencer found on database.");
            throw new NotFoundApiException("sequencer.not.found");
        }

        return sequencers;
    }
}
