package br.com.onebr.model.resistome;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "trimethoprim", schema = "public")
public class Trimethoprim implements ResistomeBaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTrimethoprim")
    @SequenceGenerator(name = "seqTrimethoprim", sequenceName = "seq_resistome_att", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;
}