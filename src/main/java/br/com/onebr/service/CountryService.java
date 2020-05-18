package br.com.onebr.service;

import br.com.onebr.controller.response.CountryRes;
import br.com.onebr.enumeration.Language;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Country;
import br.com.onebr.repository.CountryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CountryRes> findAll(String language) {
        final List<Country> countries = countryRepository.findAll();

        if (CollectionUtils.isEmpty(countries)) {
            log.warn("message=No country found on database.");
            throw new NotFoundApiException("country.not.found");
        }

        final boolean isLanguagePt = Language.PT.getCode().equals(language);
        final List<CountryRes> countryResList = new ArrayList<>();
        countries.forEach(s -> countryResList.add(CountryRes.builder()
            .id(s.getId())
            .name(isLanguagePt ? s.getNamePt() : s.getNameEn())
            .build()));

        return countryResList;
    }
}
