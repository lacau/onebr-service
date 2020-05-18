package br.com.onebr.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"b_lactam", "phenicol", "colistin", "tetracycline", "glycopeptide", "aminoglycoside", "fosfomycin", "trimethoprim",
    "nitroimidazole", "macrolide", "quinolone", "sulphonamide", "rifampicin", "fusidic_acid", "oxazolidinone"})
public class ResistomeReq {

    @JsonProperty("b_lactam")
    private List<Long> bLactamList;

    @JsonProperty("phenicol")
    private List<Long> phenicolList;

    @JsonProperty("colistin")
    private List<Long> colistinList;

    @JsonProperty("tetracycline")
    private List<Long> tetracyclineList;

    @JsonProperty("glycopeptide")
    private List<Long> glycopeptideList;

    @JsonProperty("aminoglycoside")
    private List<Long> aminoglycosideList;

    @JsonProperty("fosfomycin")
    private List<Long> fosfomycinList;

    @JsonProperty("trimethoprim")
    private List<Long> trimethoprimList;

    @JsonProperty("nitroimidazole")
    private List<Long> nitroimidazoleList;

    @JsonProperty("macrolide")
    private List<Long> macrolideList;

    @JsonProperty("quinolone")
    private List<Long> quinoloneList;

    @JsonProperty("sulphonamide")
    private List<Long> sulphonamideList;

    @JsonProperty("rifampicin")
    private List<Long> rifampicinList;

    @JsonProperty("fusidic_acid")
    private List<Long> fusidicAcidList;

    @JsonProperty("oxazolidinone")
    private List<Long> oxazolidinoneList;
}
