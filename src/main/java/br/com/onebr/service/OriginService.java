package br.com.onebr.service;

import br.com.onebr.controller.response.OriginRes;
import br.com.onebr.enumeration.Language;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Origin;
import br.com.onebr.repository.OriginRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class OriginService {

    @Autowired
    private OriginRepository originRepository;

    public List<OriginRes> findAll(String language) {
        final List<Origin> origins = originRepository.findAll();

        if (CollectionUtils.isEmpty(origins)) {
            log.warn("message=No origin found on database.");
            throw new NotFoundApiException("origin.not.found");
        }

        final boolean isLanguagePt = Language.PT.getCode().equals(language);
        final List<OriginRes> originResList = new ArrayList<>();
        origins.forEach(o -> originResList.add(OriginRes.builder()
            .id(o.getId())
            .name(isLanguagePt ? o.getNamePt() : o.getNameEn())
            .build()));

        return originResList;
    }
}
