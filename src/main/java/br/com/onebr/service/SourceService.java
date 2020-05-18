package br.com.onebr.service;

import br.com.onebr.controller.response.SourceRes;
import br.com.onebr.enumeration.Language;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Source;
import br.com.onebr.repository.SourceRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class SourceService {

    @Autowired
    private SourceRepository sourceRepository;

    public List<SourceRes> findAll(String language) {
        final List<Source> sources = sourceRepository.findAll();

        if (CollectionUtils.isEmpty(sources)) {
            log.warn("message=No source found on database.");
            throw new NotFoundApiException("source.not.found");
        }

        final boolean isLanguagePt = Language.PT.getCode().equals(language);
        final List<SourceRes> sourceResList = new ArrayList<>();
        sources.forEach(s -> sourceResList.add(SourceRes.builder()
            .id(s.getId())
            .name(isLanguagePt ? s.getNamePt() : s.getNameEn())
            .build()));

        return sourceResList;
    }
}
