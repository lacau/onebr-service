package br.com.onebr.model.config;

import br.com.onebr.model.security.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Date;
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
@Table(name = "publication", schema = "public")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPublication")
    @SequenceGenerator(name = "seqPublication", sequenceName = "seq_publication", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PublicationType type;

    @Column(name = "\"order\"")
    private Short order;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "title_pt")
    private String titlePt;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "description_pt")
    private String descriptionPt;

    @Column(name = "link")
    private String link;

    @Column(name = "date")
    private Date date;

    @OneToOne
    @JoinColumn(name = "fk_user")
    private User user;

    @Column(name = "active")
    private boolean active;
}
