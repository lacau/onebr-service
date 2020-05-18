package br.com.onebr.security;

import br.com.onebr.controller.response.BaseRes;
import br.com.onebr.security.OneBrConstants.ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
public class AuthenticationRes extends BaseRes implements UserDetails {

    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    private boolean accountNonExpired;

    @JsonIgnore
    private boolean credentialsNonExpired;

    @JsonIgnore
    private boolean accountNonLocked;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    public boolean isAdmin() {
        return authorities == null ? false : authorities.stream().anyMatch(a -> ROLE.ADMIN.equals(a.getAuthority()));
    }

    public AuthenticationRes(Long id, String name, String username, String password, boolean enabled, boolean accountNonExpired,
        boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }
}
