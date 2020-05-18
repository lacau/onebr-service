package br.com.onebr.service;

import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Specie;
import br.com.onebr.repository.SpecieRepository;
import br.com.onebr.security.AuthenticationRes;
import br.com.onebr.service.util.SecurityUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class SpecieService {

    @Autowired
    private SpecieRepository specieRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public List<Specie> findAllByLoggedUser() {
        final AuthenticationRes auth = securityUtil.getAuthentication();
        List<Specie> species;
        if (auth.isAdmin()) {
            species = specieRepository.findAllByOrderById();
        } else {
            species = specieRepository.findAllByUserIdOrderById(auth.getId());
        }

        if (CollectionUtils.isEmpty(species)) {
            log.warn("message=No specie found on database.");
            throw new NotFoundApiException("specie.not.found");
        }

        return species;
    }
}
