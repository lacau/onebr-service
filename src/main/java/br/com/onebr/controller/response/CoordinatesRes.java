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
public class CoordinatesRes {

    @JsonProperty("coordinates")
    private List<CoordinateRes> coordinates;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoordinateRes {

        private Long id;

        private Float latitude;

        private Float longitude;
    }
}
