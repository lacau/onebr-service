package br.com.onebr.controller;

import br.com.onebr.controller.response.CoordinatesRes;
import br.com.onebr.enumeration.BacteriaType;
import br.com.onebr.service.MapService;
import br.com.onebr.service.util.ApiOneBr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/map", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/coordinates")
    public ResponseEntity<CoordinatesRes> getCoordinates(@RequestParam("bacteria") BacteriaType bacteriaType) {
        final CoordinatesRes coordinatesRes = mapService.findAllCoordinates(bacteriaType);

        return ResponseEntity.ok(coordinatesRes);
    }
}
