package br.com.onebr.controller.response;

import br.com.onebr.model.Antibiogram;
import br.com.onebr.model.City;
import br.com.onebr.model.ClermontTyping;
import br.com.onebr.model.HeavyMetal;
import br.com.onebr.model.Plasmidome;
import br.com.onebr.model.Sequencer;
import br.com.onebr.model.Serotype;
import br.com.onebr.model.Serovar;
import br.com.onebr.model.Specie;
import br.com.onebr.model.Virulome;
import br.com.onebr.model.resistome.Resistome;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BacteriaViewRes {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("identification")
    private String identification;

    @JsonProperty("researcher_name")
    private String researcherName;

    @JsonProperty("specie")
    private Specie specie;

    @JsonProperty("country")
    private CountryRes country;

    @JsonProperty("region")
    private RegionRes region;

    @JsonProperty("city")
    private City city;

    @JsonProperty("geo_location_lat")
    private Double geolocationLat;

    @JsonProperty("geo_location_long")
    private Double geolocationLong;

    @JsonProperty("date")
    private String date;

    @JsonProperty("origin")
    private OriginRes origin;

    @JsonProperty("source")
    private SourceRes source;

    @JsonProperty("host")
    private String host;

    @JsonProperty("st")
    private String st;

    @JsonProperty("plasmidome")
    private Set<Plasmidome> plamidomes;

    @JsonProperty("virulome")
    private Set<Virulome> virulomes;

    @JsonProperty("resistome")
    private Resistome resistome;

    @JsonProperty("k_locus")
    private String kLocus;

    @JsonProperty("wzi")
    private String wzi;

    @JsonProperty("wzc")
    private String wzc;

    @JsonProperty("fim_type")
    private String fimType;

    @JsonProperty("clermont_typing")
    private ClermontTyping clermontTyping;

    @JsonProperty("serotype")
    private Serotype serotype;

    @JsonProperty("serovar")
    private Serovar serovar;

    @JsonProperty("heavy_metal")
    private Set<HeavyMetal> heavyMetal;

    @JsonProperty("antibiogram")
    private Antibiogram antibiogram;

    @JsonProperty("sequencer")
    private Sequencer sequencer;

    @JsonProperty("sequencing_date")
    private Date sequencingDate;

    @JsonProperty("assembler")
    private String assembler;

    @JsonProperty("date_of_assembly")
    private Date dateOfAssembly;

    @JsonProperty("genome_bp")
    private Integer genomeBp;

    @JsonProperty("contigs_no")
    private Short contigsNo;

    @JsonProperty("access_no_gb")
    private String accessNoGb;

    @JsonProperty("paper_published")
    private String paperPublished;
}
