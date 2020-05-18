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
public class GenericImagesRes {

    @JsonProperty("images")
    private List<GenericImageRes> members;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GenericImageRes {

        @JsonProperty("tooltip")
        private String name;

        @JsonProperty("image")
        private ImageRes image;
    }
}
