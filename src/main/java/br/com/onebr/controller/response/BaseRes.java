package br.com.onebr.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaseRes {

    @JsonProperty("token")
    private String token;
}
