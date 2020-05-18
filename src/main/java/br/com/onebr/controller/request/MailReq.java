package br.com.onebr.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailReq {

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("email")
    private String email;

    @JsonProperty("telephone")
    private String telephone;

    @NotNull
    @JsonProperty("message")
    private String message;
}
