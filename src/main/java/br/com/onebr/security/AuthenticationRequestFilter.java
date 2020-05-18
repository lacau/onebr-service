package br.com.onebr.security;

import br.com.onebr.enumeration.Scope;
import br.com.onebr.exception.ApiException;
import br.com.onebr.exception.ErrorResponse;
import br.com.onebr.exception.ForbiddenApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class AuthenticationRequestFilter extends OncePerRequestFilter {

    private final String[] SHOULD_FILTER = {
        "admin",
        "dashboard",
        "bacteria/filter"
    };

    private final String[] SHOULD_NOT_FILTER = {
        "bacteria/filter/resistome"};

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = null;

        if (token != null && token.startsWith(tokenUtil.BEARER)) {
            token = tokenUtil.sanitizeBearerToken(token);
            try {
                username = tokenUtil.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                log.error("message=Unable to get JWT token. token={}", token);
            } catch (ExpiredJwtException e) {
                log.warn("message=JWT token has expired. token={}", token);
            } catch (ForbiddenApiException e) {
                tokenUtil.revokeToken();
                sendErrorResponse(request, response, e);
                return;
            } catch (ApiException e) {
                sendErrorResponse(request, response, e);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(username);
            } catch (ApiException e) {
                sendErrorResponse(request, response, e);
                return;
            }

            if (tokenUtil.validateToken(token, userDetails)) {
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        final String uri = request.getRequestURI();
        if (uri.contains("view-data")) {
            final String scopeParam = request.getParameter("scope");

            return scopeParam != null && !Scope.valueOf(scopeParam).isRestricted();
        }

        if (Arrays.stream(SHOULD_NOT_FILTER).filter(x -> uri.contains(x)).count() != 0) {
            return true;
        }

        return Arrays.stream(SHOULD_FILTER).filter(x -> uri.contains(x)).count() == 0;
    }

    public void sendErrorResponse(ServletRequest request, ServletResponse response, ApiException e) throws IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(e.getStatus().value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.builder()
            .status(e.getStatus().value())
            .error(e.getStatus().getReasonPhrase())
            .message(e.getReason())
            .path(((HttpServletRequest) request).getRequestURI())
            .build()));
        res.getWriter().flush();
        res.getWriter().close();
    }
}
