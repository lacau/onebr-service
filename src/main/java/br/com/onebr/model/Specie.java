package br.com.onebr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "specie", schema = "public")
public class Specie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSpecie")
    @SequenceGenerator(name = "seqSpecie", sequenceName = "seq_specie", allocationSize = 1)
    @NotNull
    private Long id;

    @Column(name = "name")
    private String name;
}
