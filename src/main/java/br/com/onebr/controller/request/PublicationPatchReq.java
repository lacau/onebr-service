package br.com.onebr.controller.request;

import br.com.onebr.model.config.PublicationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationPatchReq {

    @JsonProperty("type")
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
    private String link;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("active")
    private Boolean active;
}
