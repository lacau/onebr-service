package br.com.onebr.model.query;

import br.com.onebr.controller.response.ResistomeStringRes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Calendar;
import java.util.Date;

public interface BacteriaQueryResult {

    Calendar CALENDAR = Calendar.getInstance();

    String PROJECTION = "SELECT "
        + "b.id as id, "
        + "b.identification as identification, "
        + "c.name as cityName, "
        + "o.name_en as originNameEn, "
        + "o.name_pt as originNamePt, "
        + "s.name_en as sourceNameEn, "
        + "s.name_pt as sourceNamePt, "
        + "b.host as host, "
        + "b.st as st, "
        + "b.date as date, "
        + "string_agg(distinct bl.name, ', ') as bLactam, "
        + "string_agg(distinct ph.name, ', ') as phenicol, "
        + "string_agg(distinct col.name, ', ') as colistin, "
        + "string_agg(distinct tetra.name, ', ') as tetracycline, "
        + "string_agg(distinct glyco.name, ', ') as glycopeptide, "
        + "string_agg(distinct amino.name, ', ') as aminoglycoside, "
        + "string_agg(distinct fosfo.name, ', ') as fosfomycin, "
        + "string_agg(distinct tri.name, ', ') as trimethoprim, "
        + "string_agg(distinct nitro.name, ', ') as nitroimidazole, "
        + "string_agg(distinct macro.name, ', ') as macrolide, "
        + "string_agg(distinct quino.name, ', ') as quinolone, "
        + "string_agg(distinct sulph.name, ', ') as sulphonamide, "
        + "string_agg(distinct rifa.name, ', ') as rifampicin, "
        + "string_agg(distinct fusi.name, ', ') as fusidicAcid, "
        + "string_agg(distinct oxa.name, ', ') as oxazolidinone ";

    String FROM = "FROM bacteria b "
        + "LEFT JOIN city c ON c.id = b.fk_city "
        + "LEFT JOIN origin o ON o.id = b.fk_origin "
        + "LEFT JOIN source s ON s.id = b.fk_source "
        // resistome
        + "LEFT JOIN resistome r ON r.id = b.fk_resistome "
        + "LEFT JOIN b_lactam_resistome blr ON blr.fk_resistome = r.id "
        + "LEFT JOIN b_lactam bl ON bl.id = blr.fk_b_lactam "
        + "LEFT JOIN phenicol_resistome pr ON pr.fk_resistome = r.id "
        + "LEFT JOIN phenicol ph ON ph.id = pr.fk_phenicol "
        + "LEFT JOIN colistin_resistome cr ON cr.fk_resistome = r.id "
        + "LEFT JOIN colistin col ON col.id = cr.fk_colistin "
        + "LEFT JOIN tetracycline_resistome try ON try.fk_resistome = r.id "
        + "LEFT JOIN tetracycline tetra ON tetra.id = try.fk_tetracycline "
        + "LEFT JOIN glycopeptide_resistome gr ON gr.fk_resistome = r.id "
        + "LEFT JOIN glycopeptide glyco ON glyco.id = gr.fk_glycopeptide "
        + "LEFT JOIN aminoglycoside_resistome ar ON ar.fk_resistome = r.id "
        + "LEFT JOIN aminoglycoside amino ON amino.id = ar.fk_aminoglycoside "
        + "LEFT JOIN fosfomycin_resistome fr ON fr.fk_resistome = r.id "
        + "LEFT JOIN fosfomycin fosfo ON fosfo.id = fr.fk_fosfomycin "
        + "LEFT JOIN trimethoprim_resistome trp ON trp.fk_resistome = r.id "
        + "LEFT JOIN trimethoprim tri ON tri.id = trp.fk_trimethoprim "
        + "LEFT JOIN nitroimidazole_resistome nr ON nr.fk_resistome = r.id "
        + "LEFT JOIN nitroimidazole nitro ON nitro.id = nr.fk_nitroimidazole "
        + "LEFT JOIN macrolide_resistome mr ON mr.fk_resistome = r.id "
        + "LEFT JOIN macrolide macro ON macro.id = mr.fk_macrolide "
        + "LEFT JOIN quinolone_resistome qr ON qr.fk_resistome = r.id "
        + "LEFT JOIN quinolone quino ON quino.id = qr.fk_quinolone "
        + "LEFT JOIN sulphonamide_resistome sr ON sr.fk_resistome = r.id "
        + "LEFT JOIN sulphonamide sulph ON sulph.id = sr.fk_sulphonamide "
        + "LEFT JOIN rifampicin_resistome rr ON rr.fk_resistome = r.id "
        + "LEFT JOIN rifampicin rifa ON rifa.id = rr.fk_rifampicin "
        + "LEFT JOIN fusidic_acid_resistome far ON far.fk_resistome = r.id "
        + "LEFT JOIN fusidic_acid fusi ON fusi.id = far.fk_fusidic_acid "
        + "LEFT JOIN oxazolidinone_resistome oxr ON oxr.fk_resistome = r.id "
        + "LEFT JOIN oxazolidinone oxa ON oxa.id = oxr.fk_oxazolidinone ";

    String WHERE = "WHERE b.fk_specie =:specie "
        + "AND "
        + "((:searchTerm IS NOT NULL "
        + "   AND ( "
        + "     LOWER(unaccent(c.name)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) OR "
        + "     LOWER(unaccent(b.identification)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) OR "
        + "     LOWER(o.name_en) LIKE LOWER(CONCAT('%',:searchTerm,'%')) OR LOWER(unaccent(o.name_pt)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) OR "
        + "     LOWER(s.name_en) LIKE LOWER(CONCAT('%',:searchTerm,'%')) OR LOWER(unaccent(s.name_pt)) LIKE LOWER(CONCAT('%',unaccent(:searchTerm),'%')) OR "
        + "     LOWER(b.host) LIKE LOWER(CONCAT('%',:searchTerm,'%')) OR "
        + "     LOWER(b.st) LIKE LOWER(CONCAT('%',:searchTerm,'%')) "
        + "   ) "
        + ") "
        + "OR :searchTerm IS NULL) "
        // resistome
        + "AND ( "
        + "  blr.fk_b_lactam IN (:resistomes) OR "
        + "  pr.fk_phenicol IN (:resistomes) OR "
        + "  cr.fk_colistin IN (:resistomes) OR "
        + "  try.fk_tetracycline IN (:resistomes) OR "
        + "  gr.fk_glycopeptide IN (:resistomes) OR "
        + "  ar.fk_aminoglycoside IN (:resistomes) OR "
        + "  fr.fk_fosfomycin IN (:resistomes) OR "
        + "  trp.fk_trimethoprim IN (:resistomes) OR "
        + "  nr.fk_nitroimidazole IN (:resistomes) OR "
        + "  mr.fk_macrolide IN (:resistomes) OR "
        + "  qr.fk_quinolone IN (:resistomes) OR "
        + "  sr.fk_sulphonamide IN (:resistomes) OR "
        + "  rr.fk_rifampicin IN (:resistomes) OR "
        + "  far.fk_fusidic_acid IN (:resistomes) OR "
        + "  oxr.fk_oxazolidinone IN (:resistomes) OR :resistomes IS NULL "
        + ") "
        + "AND (EXTRACT(YEAR FROM b.date) >= :yearStart OR :yearStart = 0) "
        + "AND (EXTRACT(YEAR FROM b.date) <= :yearEnd OR :yearEnd = 0) "
        + "AND (b.id IN(:ids) OR :ids IS NULL) ";

    String GROUP_BY = "GROUP BY b.id, b.identification, c.name, o.name_en, o.name_pt, s.name_en, s.name_pt, b.host, b.st";

    String QUERY = PROJECTION + FROM + WHERE + GROUP_BY;

    Long getId();

    String getIdentification();

    String getCityName();

    String getOriginNameEn();

    String getOriginNamePt();

    String getSourceNameEn();

    String getSourceNamePt();

    String getHost();

    String getSt();

    @JsonIgnore
    Date getDate();

    @JsonIgnore
    String getBLactam();

    @JsonIgnore
    String getPhenicol();

    @JsonIgnore
    String getColistin();

    @JsonIgnore
    String getTetracycline();

    @JsonIgnore
    String getGlycopeptide();

    @JsonIgnore
    String getAminoglycoside();

    @JsonIgnore
    String getFosfomycin();

    @JsonIgnore
    String getTrimethoprim();

    @JsonIgnore
    String getNitroimidazole();

    @JsonIgnore
    String getMacrolide();

    @JsonIgnore
    String getQuinolone();

    @JsonIgnore
    String getSulphonamide();

    @JsonIgnore
    String getRifampicin();

    @JsonIgnore
    String getFusidicAcid();

    @JsonIgnore
    String getOxazolidinone();

    @JsonProperty("date")
    default Integer getDateString() {
        final Date date = getDate();
        if (date != null) {
            CALENDAR.setTime(date);
            return CALENDAR.get(Calendar.YEAR);
        }

        return null;
    }

    default ResistomeStringRes getResistome() {
        return ResistomeStringRes.builder()
            .bLactamSet(getBLactam())
            .phenicolSet(getPhenicol())
            .colistinSet(getColistin())
            .tetracyclineSet(getTetracycline())
            .glycopeptideSet(getGlycopeptide())
            .aminoglycosideSet(getAminoglycoside())
            .fosfomycinSet(getFosfomycin())
            .trimethoprimSet(getTrimethoprim())
            .nitroimidazoleSet(getNitroimidazole())
            .macrolideSet(getMacrolide())
            .quinoloneSet(getQuinolone())
            .sulphonamideSet(getSulphonamide())
            .rifampicinSet(getRifampicin())
            .fusidicAcidSet(getFusidicAcid())
            .oxazolidinoneSet(getOxazolidinone())
            .build();
    }
}
