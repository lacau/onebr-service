package br.com.onebr.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "serotype", schema = "public")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Serotype {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSerotype")
    @SequenceGenerator(name = "seqSerotype", sequenceName = "seq_serotype", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fk_antigen_o")
    private AntigenO antigenO;

    @OneToOne
    @JoinColumn(name = "fk_antigen_h")
    private AntigenH antigenH;
}
