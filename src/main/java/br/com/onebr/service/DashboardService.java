package br.com.onebr.service;

import br.com.onebr.controller.response.DashboardMenuRes;
import br.com.onebr.controller.response.DashboardOptionsRes;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.dashboard.DashboardMenu;
import br.com.onebr.model.dashboard.DashboardOptions;
import br.com.onebr.repository.DashboardMenuRepository;
import br.com.onebr.repository.DashboardOptionsRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DashboardService {

    private static final String DASHBOARD_MENU_CACHE = "dashboard-menu";

    private static final String EXPERIMENTS_KEY = "admin.experiments";

    @Autowired
    private DashboardMenuRepository dashboardMenuRepository;

    @Autowired
    private DashboardOptionsRepository dashboardOptionsRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SpecieService specieService;

    @Cacheable(value = DASHBOARD_MENU_CACHE, key = "{#isAdmin, #language}")
    public DashboardMenuRes findDashboardMenu(boolean isAdmin, String language) {
        final DashboardMenuRes dashboardMenuRes = DashboardMenuRes.builder()
            .language(language)
            .build();

        final List<DashboardMenu> dashboardMenuList = dashboardMenuRepository.findByAdminAndActiveIsTrueOrderByOrder(isAdmin);
        dashboardMenuRes.addItem(dashboardMenuList);

        if (dashboardMenuRes.getItems().isEmpty()) {
            log.warn("message=No dashboard menu found. admin={}, language={}", language);
            throw new NotFoundApiException("resource.not.found");
        }

        return dashboardMenuRes;
    }

    public DashboardOptionsRes findDashboardOptions(boolean isAdmin, String language) {
        final DashboardOptionsRes dashboardMenuRes = DashboardOptionsRes.builder()
            .language(language)
            .build();

        final List<DashboardOptions> dashboardMenuList = dashboardOptionsRepository.findByAdminAndActiveIsTrueOrderByOrder(isAdmin);
        dashboardMenuRes.addItem(dashboardMenuList);

        if (dashboardMenuRes.getItems().isEmpty()) {
            log.warn("message=No dashboard menu found. admin={}, language={}", language);
            throw new NotFoundApiException("resource.not.found");
        }

        try {
            specieService.findAllByLoggedUser();
        } catch (NotFoundApiException e) {
            dashboardMenuRes.getItems().removeIf(i -> EXPERIMENTS_KEY.equals(i.getKey()));
            log.info("message=User is not associated with a specie");
        } finally {
            return dashboardMenuRes;
        }
    }
}
