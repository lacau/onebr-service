package br.com.onebr.model.resistome;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "resistome", schema = "public")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Resistome {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqResistome")
    @SequenceGenerator(name = "seqResistome", sequenceName = "seq_resistome", allocationSize = 1)
    private Long id;

    @ManyToMany
    @JoinTable(name = "b_lactam_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_b_lactam"))
    private Set<BLactam> bLactamSet;

    @ManyToMany
    @JoinTable(name = "phenicol_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_phenicol"))
    private Set<Phenicol> phenicolSet;

    @ManyToMany
    @JoinTable(name = "colistin_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_colistin"))
    private Set<Colistin> colistinSet;

    @ManyToMany
    @JoinTable(name = "tetracycline_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_tetracycline"))
    private Set<Tetracycline> tetracyclineSet;

    @ManyToMany
    @JoinTable(name = "glycopeptide_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_glycopeptide"))
    private Set<Glycopeptide> glycopeptideSet;

    @ManyToMany
    @JoinTable(name = "aminoglycoside_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_aminoglycoside"))
    private Set<Aminoglycoside> aminoglycosideSet;

    @ManyToMany
    @JoinTable(name = "fosfomycin_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_fosfomycin"))
    private Set<Fosfomycin> fosfomycinSet;

    @ManyToMany
    @JoinTable(name = "trimethoprim_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_trimethoprim"))
    private Set<Trimethoprim> trimethoprimSet;

    @ManyToMany
    @JoinTable(name = "nitroimidazole_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_nitroimidazole"))
    private Set<Nitroimidazole> nitroimidazoleSet;

    @ManyToMany
    @JoinTable(name = "macrolide_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_macrolide"))
    private Set<Macrolide> macrolideSet;

    @ManyToMany
    @JoinTable(name = "quinolone_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_quinolone"))
    private Set<Quinolone> quinoloneSet;

    @ManyToMany
    @JoinTable(name = "sulphonamide_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_sulphonamide"))
    private Set<Sulphonamide> sulphonamideSet;

    @ManyToMany
    @JoinTable(name = "rifampicin_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_rifampicin"))
    private Set<Rifampicin> rifampicinSet;

    @ManyToMany
    @JoinTable(name = "fusidic_acid_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_fusidic_acid"))
    private Set<FusidicAcid> fusidicAcidSet;

    @ManyToMany
    @JoinTable(name = "oxazolidinone_resistome", joinColumns = @JoinColumn(name = "fk_resistome"), inverseJoinColumns = @JoinColumn(name = "fk_oxazolidinone"))
    private Set<Oxazolidinone> oxazolidinoneSet;
}
