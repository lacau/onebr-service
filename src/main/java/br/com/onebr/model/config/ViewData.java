package br.com.onebr.model.config;

import br.com.onebr.enumeration.Scope;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
@Table(name = "view_data", schema = "public")
public class ViewData {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "key")
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope")
    private Scope scope;

    @Column(name = "content_en")
    private String contentEn;

    @Column(name = "content_pt")
    private String contentPt;

    @Column(name = "admin")
    private boolean admin;
}
