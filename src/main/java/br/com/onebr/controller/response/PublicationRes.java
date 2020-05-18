package br.com.onebr.controller.response;

import br.com.onebr.enumeration.Language;
import br.com.onebr.model.config.Publication;
import br.com.onebr.model.config.PublicationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicationRes {

    @JsonProperty("type")
    private PublicationType type;

    @JsonProperty("title")
    private String title;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("description")
    private String description;

    @JsonProperty("link")
    private String link;

    @JsonIgnore
    private String language;

    public PublicationRes(String language) {
        this.language = language;
    }

    public PublicationRes build(Publication publication) {
        final String title = publication.getTitleEn() != null ? publication.getTitleEn() : publication.getTitlePt();
        final String description = publication.getDescriptionEn() != null ? publication.getDescriptionEn() : publication.getDescriptionPt();

        this.type = publication.getType();
        this.title = Language.PT.getCode().equals(language) ? publication.getTitlePt() : title;
        this.description = Language.PT.getCode().equals(language) ? publication.getDescriptionPt() : description;
        this.link = publication.getLink();
        this.date = publication.getDate();

        return this;
    }
}
