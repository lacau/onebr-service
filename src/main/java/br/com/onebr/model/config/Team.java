package br.com.onebr.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "team", schema = "public")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTeam")
    @SequenceGenerator(name = "seqTeam", sequenceName = "seq_team", allocationSize = 1)
    private Long id;

    @Column(name = "order")
    private short order;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "title")
    private TeamTitle title;

    @Column(name = "curriculum_link")
    private String curriculumLink;

    @OneToOne
    @JoinColumn(name = "fk_image")
    private Image image;

    @Column(name = "active")
    private boolean active;
}
