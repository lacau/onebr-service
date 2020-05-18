package br.com.onebr.controller.response;

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
public class ContributorsRes {

    @JsonProperty("contributors")
    private List<ContributorRes> contributors;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContributorRes {

        @JsonProperty("order")
        private short order;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;
    }
}
