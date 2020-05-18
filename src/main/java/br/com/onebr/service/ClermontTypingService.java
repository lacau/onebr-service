package br.com.onebr.service;

import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.ClermontTyping;
import br.com.onebr.repository.ClermontTypingRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class ClermontTypingService {

    @Autowired
    private ClermontTypingRepository clermontTypingRepository;

    public List<ClermontTyping> findAll() {
        final List<ClermontTyping> clermontTypings = clermontTypingRepository.findAll();

        if (CollectionUtils.isEmpty(clermontTypings)) {
            log.warn("message=No clermont typing found on database.");
            throw new NotFoundApiException("clermont.typing.not.found");
        }

        return clermontTypings;
    }
}
