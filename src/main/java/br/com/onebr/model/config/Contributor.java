package br.com.onebr.model.config;

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
@Table(name = "contributor", schema = "public")
public class Contributor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqContributor")
    @SequenceGenerator(name = "seqContributor", sequenceName = "seq_contributor", allocationSize = 1)
    private Long id;

    @Column(name = "order")
    private short order;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active;
}
