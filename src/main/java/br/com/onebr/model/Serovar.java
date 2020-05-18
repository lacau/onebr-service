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
@Table(name = "serovar", schema = "public")
public class Serovar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSerovar")
    @SequenceGenerator(name = "seqSerovar", sequenceName = "seq_serovar", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;
}
