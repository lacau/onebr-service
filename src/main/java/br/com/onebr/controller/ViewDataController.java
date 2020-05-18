package br.com.onebr.controller;

import br.com.onebr.controller.response.ViewDataRes;
import br.com.onebr.enumeration.Scope;
import br.com.onebr.security.OneBrConstants.ROLE;
import br.com.onebr.service.ViewDataService;
import br.com.onebr.service.util.ApiOneBr;
import br.com.onebr.service.util.RequestContextUtil;
import br.com.onebr.service.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/view-data", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class ViewDataController {

    @Autowired
    private ViewDataService viewDataService;

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping
    public ResponseEntity<ViewDataRes> getViewData(@RequestParam("scope") Scope scope,
        @RequestParam(value = "key", required = false) String key) {
        final String language = RequestContextUtil.getInstance().getLocale().getLanguage();
        final ViewDataRes viewDataRes = viewDataService.findViewData(scope, key, language, securityUtil.getAuthenticationOrAnonymous().getUsername());

        return ResponseEntity.ok(viewDataRes);
    }

    @GetMapping("/admin/evict")
    @PreAuthorize("hasAuthority('" + ROLE.ADMIN + "')")
    @ApiIgnore
    public ResponseEntity getEvict(@RequestParam("scope") Scope scope) {
        viewDataService.evictCache();

        return ResponseEntity.ok().build();
    }
}
