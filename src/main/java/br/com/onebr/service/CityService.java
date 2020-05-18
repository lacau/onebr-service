package br.com.onebr.service;

import br.com.onebr.controller.response.CityRes;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.City;
import br.com.onebr.repository.CityRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<CityRes> findByNameAndCountry(String name, Long countryId) {
        final List<City> cities = cityRepository.findByNameLikeAndCountryId(name, countryId);

        if (CollectionUtils.isEmpty(cities)) {
            log.warn("message=No city found on database. name={}, countryId={}", name, countryId);
            throw new NotFoundApiException("city.not.found");
        }

        final List<CityRes> cityResList = new ArrayList<>();
        cities.forEach(c -> cityResList.add(CityRes.builder()
            .id(c.getId())
            .name(c.getName())
            .build()));

        return cityResList;
    }
}
