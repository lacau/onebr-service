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
@Table(name = "antibiogram", schema = "public")
public class Antibiogram {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAntibiogram")
    @SequenceGenerator(name = "seqAntibiogram", sequenceName = "seq_antibiogram", allocationSize = 1)
    private Long id;

    @Column(name = "mer")
    private String mer;

    @Column(name = "etp")
    private String etp;

    @Column(name = "ipm")
    private String ipm;

    @Column(name = "cro")
    private String cro;

    @Column(name = "caz")
    private String caz;

    @Column(name = "cfx")
    private String cfx;

    @Column(name = "cpm")
    private String cpm;

    @Column(name = "ctx")
    private String ctx;

    @Column(name = "nal")
    private String nal;

    @Column(name = "cip")
    private String cip;

    @Column(name = "amc")
    private String amc;

    @Column(name = "atm")
    private String atm;

    @Column(name = "ami")
    private String ami;

    @Column(name = "gen")
    private String gen;

    @Column(name = "sxt")
    private String sxt;

    @Column(name = "eno")
    private String eno;

    @Column(name = "chl")
    private String chl;

    @Column(name = "fos")
    private String fos;

    @Column(name = "cep")
    private String cep;

    @Column(name = "ctf")
    private String ctf;

    @Column(name = "amp")
    private String amp;

    @Column(name = "tet")
    private String tet;

    @Column(name = "col")
    private String col;
}
