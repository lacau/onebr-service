package br.com.onebr.controller;

import br.com.onebr.controller.response.CityRes;
import br.com.onebr.controller.response.CountryRes;
import br.com.onebr.controller.response.RegionRes;
import br.com.onebr.security.OneBrConstants.ROLE;
import br.com.onebr.service.CityService;
import br.com.onebr.service.CountryService;
import br.com.onebr.service.RegionService;
import br.com.onebr.service.util.ApiOneBr;
import br.com.onebr.service.util.RequestContextUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class RegionController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CityService cityService;

    @GetMapping("/admin/country")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<CountryRes>> getCountry() {
        final List<CountryRes> countryRes = countryService.findAll(RequestContextUtil.getInstance().getLocale().getLanguage());

        return ResponseEntity.ok(countryRes);
    }

    @GetMapping("/admin/region")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<RegionRes>> getRegion() {
        final List<RegionRes> regionRes = regionService.findAll(RequestContextUtil.getInstance().getLocale().getLanguage());

        return ResponseEntity.ok(regionRes);
    }

    @GetMapping("/admin/city")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<CityRes>> getCity(@RequestParam("name") String name, @RequestParam("countryId") Long countryId) {
        final List<CityRes> cityRes = cityService.findByNameAndCountry(name, countryId);

        return ResponseEntity.ok(cityRes);
    }
}
