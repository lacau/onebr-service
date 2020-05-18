package br.com.onebr.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
public class ResistomeStringRes {

    @JsonProperty("b_lactam")
    private String bLactamSet;

    @JsonProperty("phenicol")
    private String phenicolSet;

    @JsonProperty("colistin")
    private String colistinSet;

    @JsonProperty("tetracycline")
    private String tetracyclineSet;

    @JsonProperty("glycopeptide")
    private String glycopeptideSet;

    @JsonProperty("aminoglycoside")
    private String aminoglycosideSet;

    @JsonProperty("fosfomycin")
    private String fosfomycinSet;

    @JsonProperty("trimethoprim")
    private String trimethoprimSet;

    @JsonProperty("nitroimidazole")
    private String nitroimidazoleSet;

    @JsonProperty("macrolide")
    private String macrolideSet;

    @JsonProperty("quinolone")
    private String quinoloneSet;

    @JsonProperty("sulphonamide")
    private String sulphonamideSet;

    @JsonProperty("rifampicin")
    private String rifampicinSet;

    @JsonProperty("fusidic_acid")
    private String fusidicAcidSet;

    @JsonProperty("oxazolidinone")
    private String oxazolidinoneSet;
}
