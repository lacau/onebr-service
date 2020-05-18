package br.com.onebr.model;

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
@Table(name = "antigen_h", schema = "public")
public class AntigenH {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAntigenH")
    @SequenceGenerator(name = "seqAntigenH", sequenceName = "seq_antigen_h", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;
}
