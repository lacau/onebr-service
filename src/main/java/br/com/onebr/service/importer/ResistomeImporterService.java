package br.com.onebr.service.importer;

import br.com.onebr.model.resistome.Aminoglycoside;
import br.com.onebr.model.resistome.BLactam;
import br.com.onebr.model.resistome.Colistin;
import br.com.onebr.model.resistome.Fosfomycin;
import br.com.onebr.model.resistome.FusidicAcid;
import br.com.onebr.model.resistome.Glycopeptide;
import br.com.onebr.model.resistome.Macrolide;
import br.com.onebr.model.resistome.Nitroimidazole;
import br.com.onebr.model.resistome.Oxazolidinone;
import br.com.onebr.model.resistome.Phenicol;
import br.com.onebr.model.resistome.Quinolone;
import br.com.onebr.model.resistome.Resistome;
import br.com.onebr.model.resistome.ResistomeBaseModel;
import br.com.onebr.model.resistome.Rifampicin;
import br.com.onebr.model.resistome.Sulphonamide;
import br.com.onebr.model.resistome.Tetracycline;
import br.com.onebr.model.resistome.Trimethoprim;
import br.com.onebr.repository.resistome.AminoglycosideRepository;
import br.com.onebr.repository.resistome.BLactamRepository;
import br.com.onebr.repository.resistome.ColistinRepository;
import br.com.onebr.repository.resistome.FosfomycinRepository;
import br.com.onebr.repository.resistome.FusidicAcidRepository;
import br.com.onebr.repository.resistome.GlycopeptideRepository;
import br.com.onebr.repository.resistome.MacrolideRepository;
import br.com.onebr.repository.resistome.NitroimidazoleRepository;
import br.com.onebr.repository.resistome.OxazolidinoneRepository;
import br.com.onebr.repository.resistome.PhenicolRepository;
import br.com.onebr.repository.resistome.QuinoloneRepository;
import br.com.onebr.repository.resistome.ResistomeBaseRepository;
import br.com.onebr.repository.resistome.RifampicinRepository;
import br.com.onebr.repository.resistome.SulphonamideRepository;
import br.com.onebr.repository.resistome.TetracyclineRepository;
import br.com.onebr.repository.resistome.TrimethoprimRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ResistomeImporterService {

    private Map<String, ResistomeBaseModel> cacheMap = new HashMap<>();

    @Autowired
    private BLactamRepository bLactamRepository;

    @Autowired
    private PhenicolRepository phenicolRepository;

    @Autowired
    private ColistinRepository colistinRepository;

    @Autowired
    private TetracyclineRepository tetracyclineRepository;

    @Autowired
    private GlycopeptideRepository glycopeptideRepository;

    @Autowired
    private AminoglycosideRepository aminoglycosideRepository;

    @Autowired
    private FosfomycinRepository fosfomycinRepository;

    @Autowired
    private TrimethoprimRepository trimethoprimRepository;

    @Autowired
    private NitroimidazoleRepository nitroimidazoleRepository;

    @Autowired
    private MacrolideRepository macrolideRepository;

    @Autowired
    private QuinoloneRepository quinoloneRepository;

    @Autowired
    private SulphonamideRepository sulphonamideRepository;

    @Autowired
    private RifampicinRepository rifampicinRepository;

    @Autowired
    private FusidicAcidRepository fusidicAcidRepository;

    @Autowired
    private OxazolidinoneRepository oxazolidinoneRepository;

    @Autowired
    private ImporterStats importerStats;

    public Resistome findResistomesToImport(ResistomeImportDTO dto) {
        return Resistome.builder()
            .bLactamSet(findResistomeAttribute(dto.bLactam, new BLactam(), bLactamRepository))
            .phenicolSet(findResistomeAttribute(dto.phenicol, new Phenicol(), phenicolRepository))
            .colistinSet(findResistomeAttribute(dto.colistin, new Colistin(), colistinRepository))
            .tetracyclineSet(findResistomeAttribute(dto.tetracycline, new Tetracycline(), tetracyclineRepository))
            .glycopeptideSet(findResistomeAttribute(dto.glycopeptide, new Glycopeptide(), glycopeptideRepository))
            .aminoglycosideSet(findResistomeAttribute(dto.aminoglycoside, new Aminoglycoside(), aminoglycosideRepository))
            .fosfomycinSet(findResistomeAttribute(dto.fosfomycin, new Fosfomycin(), fosfomycinRepository))
            .trimethoprimSet(findResistomeAttribute(dto.trimethoprim, new Trimethoprim(), trimethoprimRepository))
            .nitroimidazoleSet(findResistomeAttribute(dto.nitroimidazole, new Nitroimidazole(), nitroimidazoleRepository))
            .macrolideSet(findResistomeAttribute(dto.macrolide, new Macrolide(), macrolideRepository))
            .quinoloneSet(findResistomeAttribute(dto.quinolone, new Quinolone(), quinoloneRepository))
            .sulphonamideSet(findResistomeAttribute(dto.sulphonamide, new Sulphonamide(), sulphonamideRepository))
            .rifampicinSet(findResistomeAttribute(dto.rifampicin, new Rifampicin(), rifampicinRepository))
            .fusidicAcidSet(findResistomeAttribute(dto.fusidicAcid, new FusidicAcid(), fusidicAcidRepository))
            .oxazolidinoneSet(findResistomeAttribute(dto.oxazolidinone, new Oxazolidinone(), oxazolidinoneRepository))
            .build();
    }

    private <T> Set<T> findResistomeAttribute(String[] names, T clazz, ResistomeBaseRepository repository) {
        final Set<T> set = new HashSet<>();
        if (names != null && names.length > 0) {
            for (String n : names) {
                final String name = n.trim();
                if (!StringUtils.isEmpty(name)) {
                    final String cacheKey = clazz.getClass().getSimpleName() + "-" + name;
                    final ResistomeBaseModel bLactam = cacheMap.get(cacheKey);
                    if (bLactam != null) {
                        set.add((T) bLactam);
                    } else {
                        final ResistomeBaseModel byName = repository.findByName(name);
                        if (byName == null) {
                            importerStats.addError(clazz.getClass().getSimpleName(), name);
                        } else {
                            set.add((T) byName);
                        }
                        cacheMap.put(cacheKey, byName);
                    }
                }
            }
        }

        return set;
    }
}
