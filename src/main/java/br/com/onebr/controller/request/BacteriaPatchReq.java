package br.com.onebr.controller.request;

import br.com.onebr.model.Antibiogram;
import br.com.onebr.model.City;
import br.com.onebr.model.ClermontTyping;
import br.com.onebr.model.HeavyMetal;
import br.com.onebr.model.Origin;
import br.com.onebr.model.Plasmidome;
import br.com.onebr.model.Region;
import br.com.onebr.model.Sequencer;
import br.com.onebr.model.Serotype;
import br.com.onebr.model.Serovar;
import br.com.onebr.model.Source;
import br.com.onebr.model.Specie;
import br.com.onebr.model.Virulome;
import br.com.onebr.model.resistome.Resistome;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BacteriaPatchReq {

    private String barcode;

    private String identification;

    private String researcherName;

    private Specie specie;

    private Region region;

    private City city;

    private Double geolocationLat;

    private Double geolocationLong;

    private Date date;

    private Origin origin;

    private Source source;

    private String host;

    private String st;

    private Set<Plasmidome> plamidomes;

    private Set<Virulome> virulomes;

    private Resistome resistome;

    private String kLocus;

    private String wzi;

    private String wzc;

    private String fimType;

    private ClermontTyping clermontTyping;

    private Serotype serotype;

    private Serovar serovar;

    private Set<HeavyMetal> heavyMetal;

    private Antibiogram antibiogram;

    private Sequencer sequencer;

    private Date sequencingDate;

    private String assembler;

    private Date dateOfAssembly;

    private Integer genomeBp;

    private Short contigsNo;

    private String accessNoGb;

    private String paperPublished;

    private boolean validMonth;
}
