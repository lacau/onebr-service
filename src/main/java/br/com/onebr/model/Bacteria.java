package br.com.onebr.model;

import br.com.onebr.model.resistome.Resistome;
import br.com.onebr.service.util.GeolocationJsonSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bacteria", schema = "public")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Bacteria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqBacteria")
    @SequenceGenerator(name = "seqBacteria", sequenceName = "seq_bacteria", allocationSize = 1)
    private Long id;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "identification")
    private String identification;

    @Column(name = "researcher_name")
    private String researcherName;

    @OneToOne
    @JoinColumn(name = "fk_specie")
    private Specie specie;

    @OneToOne
    @JoinColumn(name = "fk_region")
    private Region region;

    @OneToOne
    @JoinColumn(name = "fk_city")
    private City city;

    @Column(name = "geo_location_lat")
    @JsonSerialize(using = GeolocationJsonSerializer.class)
    private Double geolocationLat;

    @Column(name = "geo_location_long")
    @JsonSerialize(using = GeolocationJsonSerializer.class)
    private Double geolocationLong;

    @Column(name = "date", columnDefinition = "date")
    private Date date;

    @OneToOne
    @JoinColumn(name = "fk_origin")
    private Origin origin;

    @OneToOne
    @JoinColumn(name = "fk_source")
    private Source source;

    @Column(name = "host")
    private String host;

    @Column(name = "st")
    private String st;

    @ManyToMany
    @JoinTable(name = "plasmidome_bacteria", joinColumns = @JoinColumn(name = "fk_bacteria"), inverseJoinColumns = @JoinColumn(name = "fk_plasmidome"))
    private Set<Plasmidome> plamidomes;

    @ManyToMany
    @JoinTable(name = "virulome_bacteria", joinColumns = @JoinColumn(name = "fk_bacteria"), inverseJoinColumns = @JoinColumn(name = "fk_virulome"))
    private Set<Virulome> virulomes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_resistome")
    private Resistome resistome;

    @Column(name = "k_locus")
    private String kLocus;

    @Column(name = "wzi")
    private String wzi;

    @Column(name = "wzc")
    private String wzc;

    @Column(name = "fim_type")
    private String fimType;

    @OneToOne
    @JoinColumn(name = "fk_clermont_typing")
    private ClermontTyping clermontTyping;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_serotype")
    private Serotype serotype;

    @OneToOne
    @JoinColumn(name = "fk_serovar")
    private Serovar serovar;

    @ManyToMany
    @JoinTable(name = "heavy_metal_bacteria", joinColumns = @JoinColumn(name = "fk_bacteria"), inverseJoinColumns = @JoinColumn(name = "fk_heavy_metal"))
    private Set<HeavyMetal> heavyMetal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_antibiogram")
    private Antibiogram antibiogram;

    @OneToOne
    @JoinColumn(name = "fk_sequencer")
    private Sequencer sequencer;

    @Column(name = "sequencing_date", columnDefinition = "date")
    private Date sequencingDate;

    @Column(name = "assembler")
    private String assembler;

    @Column(name = "date_of_assembly", columnDefinition = "date")
    private Date dateOfAssembly;

    @Column(name = "genome_bp")
    private Integer genomeBp;

    @Column(name = "contigs_no")
    private Short contigsNo;

    @Column(name = "access_no_gb")
    private String accessNoGb;

    @Column(name = "paper_published")
    private String paperPublished;

    @Column(name = "valid_month")
    private boolean validMonth;
}
