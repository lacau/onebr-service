package br.com.onebr.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryRes {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}
