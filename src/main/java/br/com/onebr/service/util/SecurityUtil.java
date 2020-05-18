package br.com.onebr.service.util;

import br.com.onebr.exception.ForbiddenApiException;
import br.com.onebr.security.AuthenticationRes;
import br.com.onebr.service.SpecieService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SecurityUtil {

    private static final AuthenticationRes ANONYMOUS = AuthenticationRes.builder()
        .username("anonymous")
        .build();

    @Autowired
    private SpecieService specieService;

    public void validateLoggedUserCanChangeResource(Long resourceUserId) {
        final AuthenticationRes auth = getAuthentication();
        if (!auth.isAdmin() && auth.getId() != resourceUserId) {
            log.error("message=Attempt to retrieve/update information from another user. loggedUser={}, targetUser={}", auth.getId(), resourceUserId);
            throw new ForbiddenApiException("");
        }
    }

    public void validateLoggedUserHasBacteriaAccess(Long specieId) {
        final AuthenticationRes auth = getAuthentication();
        if (!auth.isAdmin()) {
            final List<Long> specieIds = specieService.findAllByLoggedUser().stream().map(s -> s.getId()).collect(Collectors.toList());
            if (!specieIds.contains(specieId)) {
                log.error("message=Attempt to retrieve/update information from invalid specie for user. loggedUser={}, specie={}", auth.getId(),
                    specieId);
                throw new ForbiddenApiException("");
            }
        }
    }

    public AuthenticationRes getAuthenticationOrAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return ANONYMOUS;
        }

        return (AuthenticationRes) authentication.getPrincipal();
    }

    public AuthenticationRes getAuthentication() {
        return (AuthenticationRes) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
