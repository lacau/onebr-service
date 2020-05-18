package br.com.onebr.controller.response;

import br.com.onebr.model.resistome.Aminoglycoside;
import br.com.onebr.model.resistome.BLactam;
import br.com.onebr.model.resistome.Colistin;
import br.com.onebr.model.resistome.Fosfomycin;
import br.com.onebr.model.resistome.FusidicAcid;
import br.com.onebr.model.resistome.Glycopeptide;
import br.com.onebr.model.resistome.Macrolide;
import br.com.onebr.model.resistome.Nitroimidazole;
import br.com.onebr.model.resistome.Oxazolidinone;
import br.com.onebr.model.resistome.Phenicol;
import br.com.onebr.model.resistome.Quinolone;
import br.com.onebr.model.resistome.Rifampicin;
import br.com.onebr.model.resistome.Sulphonamide;
import br.com.onebr.model.resistome.Tetracycline;
import br.com.onebr.model.resistome.Trimethoprim;
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
public class ResistomeRes {

    @JsonProperty("b_lactam")
    private List<BLactam> bLactamList;

    @JsonProperty("phenicol")
    private List<Phenicol> phenicolList;

    @JsonProperty("colistin")
    private List<Colistin> colistinList;

    @JsonProperty("tetracycline")
    private List<Tetracycline> tetracyclineList;

    @JsonProperty("glycopeptide")
    private List<Glycopeptide> glycopeptideList;

    @JsonProperty("aminoglycoside")
    private List<Aminoglycoside> aminoglycosideList;

    @JsonProperty("fosfomycin")
    private List<Fosfomycin> fosfomycinList;

    @JsonProperty("trimethoprim")
    private List<Trimethoprim> trimethoprimList;

    @JsonProperty("nitroimidazole")
    private List<Nitroimidazole> nitroimidazoleList;

    @JsonProperty("macrolide")
    private List<Macrolide> macrolideList;

    @JsonProperty("quinolone")
    private List<Quinolone> quinoloneList;

    @JsonProperty("sulphonamide")
    private List<Sulphonamide> sulphonamideList;

    @JsonProperty("rifampicin")
    private List<Rifampicin> rifampicinList;

    @JsonProperty("fusidic_acid")
    private List<FusidicAcid> fusidicAcidList;

    @JsonProperty("oxazolidinone")
    private List<Oxazolidinone> oxazolidinoneList;
}
