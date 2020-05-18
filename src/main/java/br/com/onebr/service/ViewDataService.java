package br.com.onebr.service;

import br.com.onebr.controller.response.ViewDataRes;
import br.com.onebr.enumeration.Scope;
import br.com.onebr.exception.ForbiddenApiException;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.config.ViewData;
import br.com.onebr.repository.ViewDataRepository;
import br.com.onebr.service.util.SecurityUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ViewDataService {

    private static final String VIEW_DATA_CACHE = "view-data";

    @Autowired
    private ViewDataRepository viewDataRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SecurityUtil securityUtil;

    @Cacheable(value = VIEW_DATA_CACHE, key = "{#scope, #language, #username}")
    public ViewDataRes findViewData(Scope scope, String key, String language, String username /* Just for cache */) {
        final ViewDataRes viewDataRes = ViewDataRes.builder()
            .scope(scope)
            .language(language)
            .build();

        final boolean isAdmin = validateScope(scope);

        if (!StringUtils.isEmpty(key)) {
            final ViewData viewData = viewDataRepository.findOneByScopeAndKeyAndAdmin(scope, key, isAdmin);
            viewDataRes.addItem(viewData);
        } else {
            final List<ViewData> viewDataList = viewDataRepository.findByScopeAndAdminOrderByKey(scope, isAdmin);
            viewDataRes.addItem(viewDataList);
        }

        if (viewDataRes.getItems().isEmpty()) {
            log.warn("message=Attempt to retrieve invalid data. scope={}, key={}, language={}", scope, key, language);
            throw new NotFoundApiException("resource.not.found");
        }

        return viewDataRes;
    }

    private boolean validateScope(Scope scope) {
        if (scope.isRestricted()) {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
                log.error("message=Attempt to retrieve restricted scope without authentication. scope={}", scope);
                throw new ForbiddenApiException("");
            }

            return securityUtil.getAuthentication().isAdmin();
        }

        return false;
    }

    public void evictCache() {
        cacheManager.getCache(VIEW_DATA_CACHE).clear();
    }
}
