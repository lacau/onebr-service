package br.com.onebr.controller.response;

import br.com.onebr.controller.request.PublicationPostReq;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PublicationPostRes extends PublicationPostReq {

    @JsonProperty("id")
    private Long id;

    @JsonIgnore
    private PublicationPostReq publicationPostReq;

    public PublicationPostRes(Long id, PublicationPostReq publicationPostReq) {
        this.id = id;
        setType(publicationPostReq.getType());
        setOrder(publicationPostReq.getOrder());
        setDate(publicationPostReq.getDate());
        setTitlePt(publicationPostReq.getTitlePt());
        setTitleEn(publicationPostReq.getTitleEn());
        setDescriptionPt(publicationPostReq.getDescriptionPt());
        setDescriptionEn(publicationPostReq.getDescriptionEn());
        setLink(publicationPostReq.getLink());
        setActive(publicationPostReq.getActive());
    }
}
