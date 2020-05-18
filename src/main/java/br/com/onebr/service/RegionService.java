package br.com.onebr.service;

import br.com.onebr.controller.response.RegionRes;
import br.com.onebr.enumeration.Language;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Region;
import br.com.onebr.repository.RegionRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<RegionRes> findAll(String language) {
        final List<Region> regions = regionRepository.findAll();

        if (CollectionUtils.isEmpty(regions)) {
            log.warn("message=No region found on database.");
            throw new NotFoundApiException("region.not.found");
        }

        final boolean isLanguagePt = Language.PT.getCode().equals(language);
        final List<RegionRes> regionResList = new ArrayList<>();
        regions.forEach(s -> regionResList.add(RegionRes.builder()
            .id(s.getId())
            .name(isLanguagePt ? s.getNamePt() : s.getNameEn())
            .build()));

        return regionResList;
    }
}
