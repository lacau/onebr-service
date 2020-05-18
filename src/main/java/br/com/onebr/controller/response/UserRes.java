package br.com.onebr.controller.response;

import br.com.onebr.model.Specie;
import br.com.onebr.model.config.Profile;
import br.com.onebr.model.security.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRes {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("profile")
    private Profile profile;

    @JsonProperty("species")
    private Set<Specie> species;

    public UserRes build(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.active = user.isActive();
        this.profile = user.getProfile();
        this.species = user.getSpecies();

        return this;
    }
}
