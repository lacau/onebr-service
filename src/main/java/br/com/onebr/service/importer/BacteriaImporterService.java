package br.com.onebr.service.importer;

import br.com.onebr.model.Antibiogram;
import br.com.onebr.model.Bacteria;
import br.com.onebr.model.City;
import br.com.onebr.model.ClermontTyping;
import br.com.onebr.model.HeavyMetal;
import br.com.onebr.model.Origin;
import br.com.onebr.model.Plasmidome;
import br.com.onebr.model.Region;
import br.com.onebr.model.Sequencer;
import br.com.onebr.model.Serotype;
import br.com.onebr.model.Serovar;
import br.com.onebr.model.Source;
import br.com.onebr.model.Specie;
import br.com.onebr.model.Virulome;
import br.com.onebr.repository.AntigenHRepository;
import br.com.onebr.repository.AntigenORepository;
import br.com.onebr.repository.BacteriaRepository;
import br.com.onebr.repository.CityRepository;
import br.com.onebr.repository.ClermontTypingRepository;
import br.com.onebr.repository.HeavyMetalRepository;
import br.com.onebr.repository.OriginRepository;
import br.com.onebr.repository.PlasmidomeRepository;
import br.com.onebr.repository.RegionRepository;
import br.com.onebr.repository.SequencerRepository;
import br.com.onebr.repository.SerovarRepository;
import br.com.onebr.repository.SourceRepository;
import br.com.onebr.repository.SpecieRepository;
import br.com.onebr.repository.VirulomeRepository;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class BacteriaImporterService {

    private static final String SEPERATOR = ";";

    private static final String NULL_STRING = "-";

    private final List<Bacteria> bacterias = new ArrayList<>();

    @Autowired
    private ImporterStats importerStats;

    @Autowired
    private BacteriaRepository bacteriaRepository;

    @Autowired
    private SpecieRepository specieRepository;
    private final Map<String, Specie> specieMap = new HashMap<>();

    @Autowired
    private RegionRepository regionRepository;
    private final Map<String, Region> regionMap = new HashMap<>();

    @Autowired
    private CityRepository cityRepository;
    private final Map<String, City> cityMap = new HashMap<>();

    @Autowired
    private OriginRepository originRepository;
    private final Map<String, Origin> originMap = new HashMap<>();

    @Autowired
    private SourceRepository sourceRepository;
    private final Map<String, Source> sourceMap = new HashMap<>();

    @Autowired
    private ClermontTypingRepository clermontTypingRepository;
    private final Map<String, ClermontTyping> clermontTypingMap = new HashMap<>();

    @Autowired
    private SerovarRepository serovarRepository;
    private final Map<String, Serovar> serovarMap = new HashMap<>();

    @Autowired
    private SequencerRepository sequencerRepository;
    private final Map<String, Sequencer> sequencerMap = new HashMap<>();

    @Autowired
    private HeavyMetalRepository heavyMetalRepository;

    @Autowired
    private AntigenORepository antigenORepository;

    @Autowired
    private AntigenHRepository antigenHRepository;

    @Autowired
    private PlasmidomeRepository plasmidomeRepository;

    @Autowired
    private VirulomeRepository virulomeRepository;

    @Autowired
    private ResistomeImporterService resistomeImporterService;

    public void importBacteriaCsv(MultipartFile multipartFile) throws IOException, ParseException {
        bacterias.clear();
        final String completeData = new String(multipartFile.getBytes(), "UTF-8");
        final String[] rows = completeData.split("\n");

        importerStats.setSheetName(multipartFile.getName());
        for (int i = 2; i < rows.length; i++) {
            importerStats.setCurrentLine(i + 1);
            final String[] csv = rows[i].split(SEPERATOR);
            int pos = 0;
            String barCode = csv[pos].trim();
            String identification = csv[++pos].trim();
            String researcher = csv[++pos].trim();
            String specie = csv[++pos].trim();
            // skipp since it came from city
            String country = csv[++pos].trim();
            String region = csv[++pos].trim();
            String city = csv[++pos].trim();

            final String[] latLong = csv[++pos].split(" ");
            final boolean isEmptyLatLong = NULL_STRING.equals(csv[pos].trim());
            String latitude = null;
            String longitude = null;
            if (!isEmptyLatLong) {
                latitude = latLong[0].trim();
                longitude = latLong[1].trim();
            }

            final String[] date = csv[++pos].split(",");
            final boolean isEmptyDate = NULL_STRING.equals(csv[pos].trim());
            String dateMonth = null;
            String dateYear = null;
            if (i == 33) {
                System.out.println("");
            }

            if (!isEmptyDate) {
                dateMonth = date.length == 2 ? date[0].trim() : null;
                dateYear = date.length == 2 ? date[1].trim() : date[0].trim();
            }

            String origin = csv[++pos].trim();
            String source = csv[++pos].trim();
            String host = csv[++pos].trim();
            String st = csv[++pos].trim();
            String[] plasmidomes = csv[++pos].split(",");

            ResistomeImportDTO resistomeImportDTO = new ResistomeImportDTO();
            resistomeImportDTO.bLactam = csv[++pos].split(",");
            resistomeImportDTO.phenicol = csv[++pos].split(",");
            resistomeImportDTO.colistin = csv[++pos].split(",");
            resistomeImportDTO.tetracycline = csv[++pos].split(",");
            resistomeImportDTO.glycopeptide = csv[++pos].split(",");
            resistomeImportDTO.aminoglycoside = csv[++pos].split(",");
            resistomeImportDTO.fosfomycin = csv[++pos].split(",");
            resistomeImportDTO.trimethoprim = csv[++pos].split(",");
            resistomeImportDTO.nitroimidazole = csv[++pos].split(",");
            resistomeImportDTO.macrolide = csv[++pos].split(",");
            resistomeImportDTO.quinolone = csv[++pos].split(",");
            resistomeImportDTO.sulphonamide = csv[++pos].split(",");
            resistomeImportDTO.rifampicin = csv[++pos].split(",");
            resistomeImportDTO.fusidicAcid = csv[++pos].split(",");
            resistomeImportDTO.oxazolidinone = csv[++pos].split(",");

            String[] virulomes = csv[++pos].split(",");
            String kLocus = csv[++pos].trim();
            String wzi = csv[++pos].trim();
            String wzc = csv[++pos].trim();
            String fimType = csv[++pos].trim();
            String clermontTyping = csv[++pos].trim();
            final String[] serotype = csv[++pos].split(":");
            final boolean isEmptySerotype = NULL_STRING.equals(csv[pos].trim());
            String serovar = csv[++pos].trim();
            String[] heavyMetals = csv[++pos].split(",");
            String accessNoGb = csv[++pos].trim();
            String paperPublished = csv[++pos].trim();
            String antiMER = csv[++pos].trim();
            String antiETP = csv[++pos].trim();
            String antiIPM = csv[++pos].trim();
            String antiCRO = csv[++pos].trim();
            String antiCAZ = csv[++pos].trim();
            String antiCFX = csv[++pos].trim();
            String antiCPM = csv[++pos].trim();
            String antiCTX = csv[++pos].trim();
            String antiNAL = csv[++pos].trim();
            String antiCIP = csv[++pos].trim();
            String antiAMC = csv[++pos].trim();
            String antiATM = csv[++pos].trim();
            String antiAMI = csv[++pos].trim();
            String antiGEN = csv[++pos].trim();
            String antiSXT = csv[++pos].trim();
            String antiENO = csv[++pos].trim();
            String antiCHL = csv[++pos].trim();
            String antiFOS = csv[++pos].trim();
            String antiCEP = csv[++pos].trim();
            String antiCTF = csv[++pos].trim();
            String antiAMP = csv[++pos].trim();
            String antiTET = csv[++pos].trim();
            String antiCOL = csv[++pos].trim();
            Antibiogram antibiogram = Antibiogram.builder().mer(antiMER).etp(antiETP).ipm(antiIPM).cro(antiCRO).caz(antiCAZ).cfx(antiCFX).cpm(antiCPM)
                .ctx(antiCTX).nal(antiNAL).cip(antiCIP).amc(antiAMC).atm(antiATM).ami(antiAMI).gen(antiGEN).sxt(antiSXT).eno(antiENO).chl(antiCHL)
                .fos(antiFOS).cep(antiCEP).ctf(antiCTF).amp(antiAMP).tet(antiTET).col(antiCOL).build();

            String sequencer = csv[++pos].trim();
            final String[] sequencingDate = csv[++pos].split(",");
            final boolean isEmptySequencingDate = NULL_STRING.equals(csv[pos].trim());
            String sequencingDateMonth = null;
            String sequencingDateYear = null;
            if (!isEmptySequencingDate) {
                sequencingDateMonth = sequencingDate.length == 2 ? sequencingDate[0].trim() : null;
                sequencingDateYear = sequencingDate.length == 2 ? sequencingDate[1].trim() : date[0].trim();
            }
            String assembler = csv[++pos].trim();
            final String[] dateOfAssembly = csv[++pos].split(",");
            final boolean isEmptyDateOfAssembly = NULL_STRING.equals(csv[pos].trim());
            String dateOfAssemblyMonth = null;
            String dateOfAssemblyYear = null;
            if (!isEmptyDateOfAssembly) {
                dateOfAssemblyMonth = dateOfAssembly.length == 2 ? dateOfAssembly[0].trim() : null;
                dateOfAssemblyYear = dateOfAssembly.length == 2 ? dateOfAssembly[1].trim() : date[0].trim();
            }

            final Bacteria bacteria = Bacteria.builder().barcode(barCode).identification(identification).researcherName(researcher)
                .specie(findSpecie(specie))
                .region(NULL_STRING.equals(region) ? null : findRegion(region))
                .city(NULL_STRING.equals(city) ? null : findCity(city))
                .origin(NULL_STRING.equals(origin) ? null : findOrigin(origin))
                .source(NULL_STRING.equals(source) ? null : findSource(source))
                .geolocationLat(isEmptyLatLong ? null : Double.valueOf(latitude))
                .geolocationLong(isEmptyLatLong ? null : Double.valueOf(longitude))
                .date(isEmptyDate ? null : convertToDate(dateYear, dateMonth, "date"))
                .host(host)
                .st(st).plamidomes(findPlasmidomes(plasmidomes)).resistome(resistomeImporterService.findResistomesToImport(resistomeImportDTO))
                .virulomes(findAndSaveVirulomes(virulomes)).kLocus(kLocus).wzi(wzi).wzc(wzc)
                .fimType(fimType)
                .clermontTyping(findClermontTyping(clermontTyping))
                .serotype(isEmptySerotype ? null : findSerotype(serotype))
                .serovar(findSerovar(serovar)).heavyMetal(findheavyMetals(heavyMetals)).accessNoGb(accessNoGb)
                .paperPublished(paperPublished)
                .antibiogram(antibiogram)
                .sequencer(findSequencer(sequencer))
                .sequencingDate(isEmptySequencingDate ? null : convertToDate(sequencingDateYear, sequencingDateMonth, "Sequencing date"))
                .assembler(assembler)
                .dateOfAssembly(isEmptyDateOfAssembly ? null : convertToDate(dateOfAssemblyYear, dateOfAssemblyMonth, "Date of assembly"))
                .validMonth(dateMonth != null)
                .build();

            if (pos == csv.length - 1) {
                System.out.println("index: " + i + " continuing " + barCode);
                bacterias.add(bacteria);
                continue;
            }

            String genomeBp = csv[++pos].trim();
            String contigsNo = csv[++pos].trim();
            bacteria.setGenomeBp(NULL_STRING.equals(genomeBp) ? null : Integer.valueOf(genomeBp));
            bacteria.setContigsNo(NULL_STRING.equals(contigsNo) ? null : Short.valueOf(contigsNo));

            bacterias.add(bacteria);
            System.out.println("index: " + i + " " + barCode);
        }

        if (!importerStats.hasError()) {
            bacteriaRepository.saveAll(bacterias);
            log.info("message=Import completed! count={}", bacterias.size());
        } else {
            importerStats.printReport();
        }
    }

    private Set<Plasmidome> findPlasmidomes(String[] plasmidomes) {
        final Set<Plasmidome> plasmidomesDB = new HashSet<>();

        plasmidomesDB.addAll(plasmidomeRepository.findByNameIn(
            Arrays.stream(plasmidomes).filter(p -> !StringUtils.isEmpty(p)).map(p -> p.trim()).collect(Collectors.toList())));

        if (plasmidomes.length != plasmidomesDB.size()) {
            final List<String> collect = plasmidomesDB.stream().map(p -> p.getName().trim()).collect(Collectors.toList());
            for (String p : plasmidomes) {
                if (!collect.contains(p.trim())) {
                    importerStats.addError(Plasmidome.class.getSimpleName(), p.trim());
                }
            }
        }

        return plasmidomesDB;
    }

    private Set<Virulome> findAndSaveVirulomes(String[] virulomes) {
        final Set<Virulome> virulomesDB = new HashSet<>();

        virulomesDB.addAll(virulomeRepository.findByNameIn(
            Arrays.stream(virulomes).filter(v -> !StringUtils.isEmpty(v)).map(v -> v.trim()).collect(Collectors.toList())));

        if (virulomes.length != virulomesDB.size()) {
            final List<String> collect = virulomesDB.stream().map(v -> v.getName().trim()).collect(Collectors.toList());
            for (String v : virulomes) {
                if (!collect.contains(v.trim())) {
                    importerStats.addError(Virulome.class.getSimpleName(), v.trim());
                }
            }
        }

        return virulomesDB;
    }

    private Specie findSpecie(String name) {
        final Specie specie = specieMap.get(name);
        if (specie == null) {
            final Specie specieDB = specieRepository.findByName(name);
            if (specieDB == null) {
                importerStats.addError(Specie.class.getSimpleName(), name);
            }
            specieMap.put(name, specieDB);
            return specieDB;
        }
        return specie;
    }

    private Region findRegion(String name) {
        final Region region = regionMap.get(name);
        if (region == null) {
            final Region regionDB = regionRepository.findByName(name);
            if (regionDB == null) {
                importerStats.addError(Region.class.getSimpleName(), name);
            }
            regionMap.put(name, regionDB);
            return regionDB;
        }
        return region;
    }

    private City findCity(String name) {
        final City city = cityMap.get(name);
        if (city == null) {
            final City cityDB = cityRepository.findByName(name);
            if (cityDB == null) {
                importerStats.addError(City.class.getSimpleName(), name);
            }
            cityMap.put(name, cityDB);
            return cityDB;
        }
        return city;
    }

    private Origin findOrigin(String name) {
        final Origin origin = originMap.get(name);
        if (origin == null) {
            final Origin originDB = originRepository.findByName(name);
            if (originDB == null) {
                importerStats.addError(Origin.class.getSimpleName(), name);
            }
            originMap.put(name, originDB);
            return originDB;
        }
        return origin;
    }

    private Source findSource(String name) {
        final Source source = sourceMap.get(name);
        if (source == null) {
            final Source sourceDB = sourceRepository.findByName(name);
            if (sourceDB == null) {
                importerStats.addError(Source.class.getSimpleName(), name);
            }
            sourceMap.put(name, sourceDB);
            return sourceDB;
        }
        return source;
    }

    private ClermontTyping findClermontTyping(String name) {
        final ClermontTyping clermontTyping = clermontTypingMap.get(name);
        if (clermontTyping == null) {
            final ClermontTyping clermontTypingDB = clermontTypingRepository.findByName(name);
            if (clermontTypingDB == null) {
                importerStats.addError(ClermontTyping.class.getSimpleName(), name);
            }
            clermontTypingMap.put(name, clermontTypingDB);
            return clermontTypingDB;
        }
        return clermontTyping;
    }

    private Serovar findSerovar(String name) {
        final Serovar serovar = serovarMap.get(name);
        if (serovar == null) {
            final Serovar serovarDB = serovarRepository.findByName(name);
            if (serovarDB == null) {
                importerStats.addError(Serovar.class.getSimpleName(), name);
            }
            serovarMap.put(name, serovarDB);
            return serovarDB;
        }
        return serovar;
    }

    private Set<HeavyMetal> findheavyMetals(String[] heavyMetals) {
        final Set<HeavyMetal> heavyMetalsDB = new HashSet<>();

        heavyMetalsDB.addAll(heavyMetalRepository.findByNameIn(
            Arrays.stream(heavyMetals).filter(hm -> !StringUtils.isEmpty(hm)).map(hm -> hm.trim()).collect(Collectors.toList())));

        if (heavyMetals.length != heavyMetalsDB.size()) {
            final List<String> collect = heavyMetalsDB.stream().map(hm -> hm.getName().trim()).collect(Collectors.toList());
            for (String hm : heavyMetals) {
                if (!collect.contains(hm.trim())) {
                    importerStats.addError(HeavyMetal.class.getSimpleName(), hm.trim());
                }
            }
        }

        return heavyMetalsDB;
    }

    private Sequencer findSequencer(String name) {
        if (name.equals("")) {
            return null;
        }
        final Sequencer sequencer = sequencerMap.get(name);
        if (sequencer == null) {
            final Sequencer sequencerDB = sequencerRepository.findByName(name);
            if (sequencerDB == null) {
                importerStats.addError(Sequencer.class.getSimpleName(), name);
            }
            sequencerMap.put(name, sequencerDB);
            return sequencerDB;
        }
        return sequencer;
    }

    private Serotype findSerotype(String[] serotype) {
        Serotype serotypeDB = new Serotype();
        if (serotype.length == 1) {
            final String antigen = serotype[0].trim();
            if (!StringUtils.isEmpty(antigen)) {
                if (antigen.equals("NA")) {
                    serotypeDB.setAntigenO(antigenORepository.findByName(antigen));
                    serotypeDB.setAntigenH(antigenHRepository.findByName(antigen));
                }

                final char type = antigen.charAt(0);
                switch (type) {
                    case 'O':
                        serotypeDB.setAntigenO(antigenORepository.findByName(antigen));
                        break;
                    case 'H':
                        serotypeDB.setAntigenH(antigenHRepository.findByName(antigen));
                }
                if (serotypeDB.getAntigenO() == null && serotypeDB.getAntigenH() == null) {
                    importerStats.addError(Serotype.class.getSimpleName(), antigen);
                }

                return serotypeDB;
            }

            importerStats.addError(Serotype.class.getSimpleName(), antigen);
            return null;
        }

        if (serotype.length == 2) {
            serotypeDB.setAntigenO(antigenORepository.findByName(serotype[0].trim()));
            serotypeDB.setAntigenH(antigenHRepository.findByName(serotype[1].trim()));

            if (serotypeDB.getAntigenO() == null) {
                importerStats.addError(Serotype.class.getSimpleName(), serotype[0].trim());
            }
            if (serotypeDB.getAntigenH() == null) {
                importerStats.addError(Serotype.class.getSimpleName(), serotype[1].trim());
            }

            return serotypeDB;
        }

        return null;
    }

    private Date convertToDate(String year, String month, String field) {
        if (StringUtils.isEmpty(year) && StringUtils.isEmpty(month)) {
            return null;
        }

        final SimpleDateFormat formatter = new SimpleDateFormat("MMM", Locale.ENGLISH);

        if (!StringUtils.isEmpty(month)) {
            try {
                return java.sql.Date.valueOf(LocalDate.of(Integer.valueOf(year), formatter.parse(month).getMonth() + 1, 1));
            } catch (ParseException e) {
                importerStats.addError(field, "invalid date");
                return null;
            }
        }
        return java.sql.Date.valueOf(LocalDate.of(Integer.valueOf(year), 1, 1));
    }
}
