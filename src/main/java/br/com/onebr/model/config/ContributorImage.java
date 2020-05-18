package br.com.onebr.model.config;

import javax.persistence.Column;
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
@Table(name = "contributor_image", schema = "public")
public class ContributorImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqContributorImage")
    @SequenceGenerator(name = "seqContributorImage", sequenceName = "seq_contributor_image", allocationSize = 1)
    private Long id;

    @Column(name = "order")
    private short order;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "fk_image")
    private Image image;
}
