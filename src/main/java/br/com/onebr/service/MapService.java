package br.com.onebr.service;

import br.com.onebr.controller.response.CoordinatesRes;
import br.com.onebr.controller.response.CoordinatesRes.CoordinateRes;
import br.com.onebr.enumeration.BacteriaType;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.repository.BacteriaRepository;
import br.com.onebr.repository.CoordinateQueryResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class MapService {

    @Autowired
    private BacteriaRepository bacteriaRepository;

    public CoordinatesRes findAllCoordinates(BacteriaType bacteriaType) {
        final List<CoordinateQueryResult> coordinates = bacteriaRepository.findAllCoordinates(Long.valueOf(bacteriaType.getId()));

        if (CollectionUtils.isEmpty(coordinates)) {
            log.warn("message=No coordinates found on database.");
            throw new NotFoundApiException("coordinates.not.found");
        }

        return CoordinatesRes.builder()
            .coordinates(coordinates.stream()
                .map((c) -> CoordinateRes.builder()
                    .id(c.getId())
                    .latitude(c.getLatitude())
                    .longitude(c.getLongitude())
                    .build())
                .collect(Collectors.toList()))
            .build();
    }
}
