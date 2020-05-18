package br.com.onebr.security;

import br.com.onebr.security.OneBrConstants.ROLE;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Api
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationRes> createAuthenticationToken(@RequestBody @Valid AuthenticationReq authenticationReq) throws Exception {
        authenticate(authenticationReq.getUsername(), authenticationReq.getPassword());
        final AuthenticationRes authenticationRes = (AuthenticationRes) userDetailsService.loadUserByUsername(authenticationReq.getUsername());
        authenticationRes.setToken(tokenUtil.generateToken(authenticationRes));

        return ResponseEntity.ok(authenticationRes);
    }

    @PostMapping(value = "/user/logout")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<?> logout() {
        tokenUtil.revokeToken();

        return ResponseEntity.ok().build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
