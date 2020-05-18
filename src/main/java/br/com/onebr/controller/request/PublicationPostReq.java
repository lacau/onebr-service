package br.com.onebr.controller.request;

import br.com.onebr.model.config.PublicationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationPostReq {

    @JsonProperty("type")
    @NotNull
    private PublicationType type;

    @JsonProperty("order")
    private Short order;

    @JsonProperty("title_pt")
    private String titlePt;

    @JsonProperty("title_en")
    private String titleEn;

    @JsonProperty("description_pt")
    private String descriptionPt;

    @JsonProperty("description_en")
    private String descriptionEn;

    @JsonProperty("link")
    @NotNull
    private String link;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("active")
    @NotNull
    private Boolean active;
}
