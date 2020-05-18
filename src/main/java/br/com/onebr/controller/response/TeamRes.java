package br.com.onebr.controller.response;

import br.com.onebr.model.config.TeamTitle;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRes {

    @JsonProperty("team")
    private List<TeamMemberRes> team;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamMemberRes {

        @JsonProperty("order")
        private short order;

        @JsonProperty("title")
        private TeamTitle title;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("curriculum_link")
        private String curriculumLink;

        @JsonProperty("image")
        private ImageRes image;
    }
}
