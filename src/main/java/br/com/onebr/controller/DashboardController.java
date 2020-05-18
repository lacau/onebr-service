package br.com.onebr.controller;

import br.com.onebr.controller.response.DashboardMenuRes;
import br.com.onebr.controller.response.DashboardOptionsRes;
import br.com.onebr.security.AuthenticationRes;
import br.com.onebr.security.OneBrConstants.ROLE;
import br.com.onebr.service.DashboardService;
import br.com.onebr.service.util.ApiOneBr;
import br.com.onebr.service.util.RequestContextUtil;
import br.com.onebr.service.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping("/menu")
    @PreAuthorize("hasAnyAuthority('" + ROLE.USER + "','" + ROLE.ADMIN + "')")
    public ResponseEntity<DashboardMenuRes> getDashboardMenu() {
        AuthenticationRes auth = securityUtil.getAuthentication();
        final DashboardMenuRes dashboardMenuRes = dashboardService
            .findDashboardMenu(auth.isAdmin(), RequestContextUtil.getInstance().getLocale().getLanguage());

        return ResponseEntity.ok(dashboardMenuRes);
    }

    @GetMapping("/options")
    @PreAuthorize("hasAnyAuthority('" + ROLE.USER + "','" + ROLE.ADMIN + "')")
    public ResponseEntity<DashboardOptionsRes> getDashboardOptions() {
        AuthenticationRes auth = securityUtil.getAuthentication();
        final DashboardOptionsRes dashboardOptionsRes = dashboardService
            .findDashboardOptions(auth.isAdmin(), RequestContextUtil.getInstance().getLocale().getLanguage());

        return ResponseEntity.ok(dashboardOptionsRes);
    }
}
