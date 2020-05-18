package br.com.onebr.model.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Calendar;
import java.util.Date;

public interface BacteriaAdminQueryResult {

    Calendar CALENDAR = Calendar.getInstance();

    String PROJECTION = "SELECT "
        + "b.id as id, "
        + "b.identification as identification, "
        + "spc.name as specieName, "
        + "c.name as cityName, "
        + "o.name_en as originNameEn, "
        + "o.name_pt as originNamePt, "
        + "s.name_en as sourceNameEn, "
        + "s.name_pt as sourceNamePt, "
        + "b.host as host, "
        + "b.st as st, "
        + "b.date as date ";

    String FROM = "FROM bacteria b "
        + "JOIN specie spc ON spc.id = b.fk_specie "
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

    String WHERE = "WHERE b.fk_specie IN(:specieIds) "
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
        + "AND (EXTRACT(YEAR FROM b.date) >= :yearStart OR :yearStart = 0) "
        + "AND (EXTRACT(YEAR FROM b.date) <= :yearEnd OR :yearEnd = 0) ";

    String GROUP_BY = "GROUP BY b.id, b.identification, spc.name, c.name, o.name_en, o.name_pt, s.name_en, s.name_pt, b.host, b.st";

    String QUERY = PROJECTION + FROM + WHERE + GROUP_BY;

    Long getId();

    String getIdentification();

    String getSpecieName();

    String getCityName();

    String getOriginNameEn();

    String getOriginNamePt();

    String getSourceNameEn();

    String getSourceNamePt();

    String getHost();

    String getSt();

    @JsonIgnore
    Date getDate();

    @JsonProperty("date")
    default Integer getDateString() {
        final Date date = getDate();
        if (date != null) {
            CALENDAR.setTime(date);
            return CALENDAR.get(Calendar.YEAR);
        }

        return null;
    }
}
