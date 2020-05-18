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
@Table(name = "antigen_o", schema = "public")
public class AntigenO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAntigenO")
    @SequenceGenerator(name = "seqAntigenO", sequenceName = "seq_antigen_o", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;
}
