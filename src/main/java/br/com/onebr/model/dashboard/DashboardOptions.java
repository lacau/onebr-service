package br.com.onebr.model.dashboard;

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
@Table(name = "dashboard_options", schema = "public")
public class DashboardOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqDashboardOptions")
    @SequenceGenerator(name = "seqDashboardOptions", sequenceName = "seq_dashboard_options", allocationSize = 1)
    private Long id;

    @Column(name = "order")
    private short order;

    @Column(name = "key")
    private String key;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_pt")
    private String namePt;

    @Column(name = "admin")
    private boolean admin;

    @Column(name = "active")
    private boolean active;
}
