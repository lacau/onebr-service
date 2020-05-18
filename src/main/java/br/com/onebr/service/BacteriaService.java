package br.com.onebr.service;

import br.com.onebr.controller.request.BacteriaPatchReq;
import br.com.onebr.controller.request.BacteriaPostReq;
import br.com.onebr.controller.request.BacteriaSearchReq;
import br.com.onebr.controller.response.BacteriaViewRes;
import br.com.onebr.controller.response.CountryRes;
import br.com.onebr.controller.response.OriginRes;
import br.com.onebr.controller.response.RegionRes;
import br.com.onebr.controller.response.SourceRes;
import br.com.onebr.enumeration.Language;
import br.com.onebr.exception.ForbiddenApiException;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.Antibiogram;
import br.com.onebr.model.Bacteria;
import br.com.onebr.model.Specie;
import br.com.onebr.model.query.BacteriaAdminQueryResult;
import br.com.onebr.model.query.BacteriaQueryResult;
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
import br.com.onebr.repository.VirulomeRepository;
import br.com.onebr.security.AuthenticationRes;
import br.com.onebr.service.util.SecurityUtil;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class BacteriaService {

    final SimpleDateFormat formatterInvalidMonth = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    final SimpleDateFormat formatterEn = new SimpleDateFormat("yyyy MMM", Locale.ENGLISH);
    final SimpleDateFormat formatterPt = new SimpleDateFormat("MMMM 'de' yyyy", new Locale("pt", "BR"));

    @Autowired
    private BacteriaRepository bacteriaRepository;

    @Autowired
    private SpecieService specieService;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private PlasmidomeRepository plasmidomeRepository;

    @Autowired
    private VirulomeRepository virulomeRepository;

    @Autowired
    private ClermontTypingRepository clermontTypingRepository;

    @Autowired
    private AntigenORepository antigenORepository;

    @Autowired
    private AntigenHRepository antigenHRepository;

    @Autowired
    private SerovarRepository serovarRepository;

    @Autowired
    private HeavyMetalRepository heavyMetalRepository;

    @Autowired
    private SequencerRepository sequencerRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public Page<BacteriaQueryResult> search(BacteriaSearchReq bacteriaSearchReq) {
        return bacteriaRepository.findAllBySearchTerm(bacteriaSearchReq.getBacteriaType().getId(),
            bacteriaSearchReq.getSearchTerm(),
            bacteriaSearchReq.getResistomes(),
            bacteriaSearchReq.getYearStart(),
            bacteriaSearchReq.getYearEnd(),
            bacteriaSearchReq.getIds(),
            bacteriaSearchReq);
    }

    public Page<BacteriaAdminQueryResult> searchAdmin(BacteriaSearchReq bacteriaSearchReq) {
        List<Integer> speciesIds = specieService.findAllByLoggedUser().stream().map(s -> s.getId().intValue()).collect(Collectors.toList());

        if (bacteriaSearchReq.getBacteriaType() != null) {
            final AuthenticationRes auth = securityUtil.getAuthentication();
            if (!auth.isAdmin() && !speciesIds.contains(Long.valueOf(bacteriaSearchReq.getBacteriaType().getId()).intValue())) {
                log.error("message=Attempt to retrieve by bacteria type with permission. bacteriaType={}, userId={}",
                    bacteriaSearchReq.getBacteriaType(), auth.getId());
                throw new ForbiddenApiException("");
            }
            speciesIds = Arrays.asList(Long.valueOf(bacteriaSearchReq.getBacteriaType().getId()).intValue());
        }

        return bacteriaRepository.findAllBySearchTermForAdmin(speciesIds,
            bacteriaSearchReq.getSearchTerm(),
            bacteriaSearchReq.getYearStart(),
            bacteriaSearchReq.getYearEnd(),
            bacteriaSearchReq);
    }

    public BacteriaViewRes findByIdForView(Long id, String language) {
        final boolean isLanguagePt = Language.PT.getCode().equals(language);
        final Bacteria bacteria = bacteriaRepository.findByIdForEdit(id).orElseThrow(() -> new NotFoundApiException("bacteria.not.found"));

        final CountryRes countryRes = CountryRes.builder().build();
        Optional.ofNullable(bacteria.getCity()).ifPresent(x -> {
            countryRes.setId(x.getCountry().getId());
            countryRes.setName(isLanguagePt ? x.getCountry().getNamePt() : x.getCountry().getNameEn());
        });

        final RegionRes regionRes = RegionRes.builder().build();
        Optional.ofNullable(bacteria.getRegion()).ifPresent(x -> {
            regionRes.setId(x.getId());
            regionRes.setName(isLanguagePt ? x.getNamePt() : x.getNameEn());
        });

        final OriginRes originRes = OriginRes.builder().build();
        Optional.ofNullable(bacteria.getOrigin()).ifPresent(x -> {
            originRes.setId(x.getId());
            originRes.setName(isLanguagePt ? x.getNamePt() : x.getNameEn());
        });

        final SourceRes sourceRes = SourceRes.builder().build();
        Optional.ofNullable(bacteria.getSource()).ifPresent(x -> {
            sourceRes.setId(x.getId());
            sourceRes.setName(isLanguagePt ? x.getNamePt() : x.getNameEn());
        });

        return BacteriaViewRes.builder()
            .id(bacteria.getId())
            .barcode(bacteria.getBarcode())
            .identification(bacteria.getIdentification())
            .researcherName(bacteria.getResearcherName())
            .specie(bacteria.getSpecie())
            .country(countryRes)
            .region(regionRes)
            .city(bacteria.getCity())
            .geolocationLat(bacteria.getGeolocationLat())
            .geolocationLong(bacteria.getGeolocationLong())
            .date(formatDate(bacteria.getDate(), bacteria.isValidMonth(), isLanguagePt))
            .origin(originRes)
            .source(sourceRes)
            .host(bacteria.getHost())
            .st(bacteria.getSt())
            .plamidomes(bacteria.getPlamidomes())
            .virulomes(bacteria.getVirulomes())
            .resistome(bacteria.getResistome())
            .kLocus(bacteria.getKLocus())
            .wzi(bacteria.getWzi())
            .wzc(bacteria.getWzc())
            .fimType(bacteria.getFimType())
            .clermontTyping(bacteria.getClermontTyping())
            .serotype(bacteria.getSerotype())
            .serovar(bacteria.getSerovar())
            .heavyMetal(bacteria.getHeavyMetal())
            .antibiogram(bacteria.getAntibiogram())
            .sequencer(bacteria.getSequencer())
            .sequencingDate(bacteria.getSequencingDate())
            .assembler(bacteria.getAssembler())
            .dateOfAssembly(bacteria.getDateOfAssembly())
            .genomeBp(bacteria.getGenomeBp())
            .contigsNo(bacteria.getContigsNo())
            .accessNoGb(bacteria.getAccessNoGb())
            .paperPublished(bacteria.getPaperPublished())
            .build();
    }

    public Bacteria findByIdForEdit(Long id) {
        final Bacteria bacteria = bacteriaRepository.findByIdForEdit(id).orElseThrow(() -> new NotFoundApiException("bacteria.not.found"));

        securityUtil.validateLoggedUserHasBacteriaAccess(bacteria.getSpecie().getId());

        return bacteria;
    }

    @Transactional
    public void delete(Long id) {
        final Bacteria bacteria = bacteriaRepository.findById(id).orElseThrow(() -> new NotFoundApiException("bacteria.not.found"));
        securityUtil.validateLoggedUserHasBacteriaAccess(bacteria.getSpecie().getId());
        try {
            bacteriaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("message=Attempt to delete experiment with invalid id. id={}", id);
            throw new NotFoundApiException("experiment.not.found");
        }
    }

    @Transactional
    public Bacteria postBacteria(BacteriaPostReq bacteriaReq) {
        securityUtil.validateLoggedUserHasBacteriaAccess(bacteriaReq.getSpecie().getId());

        bacteriaReq.getAntibiogram().setId(null);
        Optional.ofNullable(bacteriaReq.getResistome()).ifPresent(x -> x.setId(null));
        Optional.ofNullable(bacteriaReq.getSerotype()).ifPresent(x -> x.setId(null));

        final Bacteria bacteria = Bacteria.builder()
            .barcode(bacteriaReq.getBarcode())
            .identification(bacteriaReq.getIdentification())
            .researcherName(bacteriaReq.getResearcherName())
            .specie(bacteriaReq.getSpecie())
            .region(bacteriaReq.getRegion())
            .city(bacteriaReq.getCity())
            .geolocationLat(bacteriaReq.getGeolocationLat())
            .geolocationLong(bacteriaReq.getGeolocationLong())
            .date(bacteriaReq.getDate())
            .origin(bacteriaReq.getOrigin())
            .source(bacteriaReq.getSource())
            .host(bacteriaReq.getHost())
            .st(bacteriaReq.getSt())
            .plamidomes(bacteriaReq.getPlamidomes())
            .virulomes(bacteriaReq.getVirulomes())
            .resistome(bacteriaReq.getResistome())
            .kLocus(bacteriaReq.getKLocus())
            .wzi(bacteriaReq.getWzi())
            .wzc(bacteriaReq.getWzc())
            .fimType(bacteriaReq.getFimType())
            .clermontTyping(bacteriaReq.getClermontTyping())
            .serotype(bacteriaReq.getSerotype())
            .serovar(bacteriaReq.getSerovar())
            .heavyMetal(bacteriaReq.getHeavyMetal())
            .antibiogram(bacteriaReq.getAntibiogram())
            .sequencer(bacteriaReq.getSequencer())
            .sequencingDate(bacteriaReq.getSequencingDate())
            .assembler(bacteriaReq.getAssembler())
            .dateOfAssembly(bacteriaReq.getDateOfAssembly())
            .genomeBp(bacteriaReq.getGenomeBp())
            .contigsNo(bacteriaReq.getContigsNo())
            .accessNoGb(bacteriaReq.getAccessNoGb())
            .paperPublished(bacteriaReq.getPaperPublished())
            .build();

        return bacteriaRepository.save(bacteria);
    }

    private String formatDate(Date date, boolean validMonth, boolean isLanguagePt) {
        if (date == null) {
            return null;
        }
        if (!validMonth) {
            return formatterInvalidMonth.format(date);
        }

        return isLanguagePt ? formatterPt.format(date) : formatterEn.format(date);
    }

    @Transactional
    public Bacteria patchBacteria(Long id, BacteriaPatchReq bacteriaReq) {
        Bacteria bacteria = bacteriaRepository.findByIdForEdit(id).orElseThrow(() -> {
            log.warn("message=Attempt to update experiment with invalid id. id={}", id);
            throw new NotFoundApiException("bacteria.not.found");
        });

        securityUtil.validateLoggedUserHasBacteriaAccess(bacteria.getSpecie().getId());

        Optional.ofNullable(bacteriaReq.getBarcode()).ifPresent(x -> bacteria.setBarcode(x));
        Optional.ofNullable(bacteriaReq.getIdentification()).ifPresent(x -> bacteria.setIdentification(x));
        Optional.ofNullable(bacteriaReq.getResearcherName()).ifPresent(x -> bacteria.setResearcherName(x));

        final Specie specie = bacteriaReq.getSpecie();
        if (specie != null && specie.getId() != null) {
            securityUtil.validateLoggedUserHasBacteriaAccess(specie.getId());
            bacteria.setSpecie(Specie.builder().id(specie.getId()).build());
        }

        Optional.ofNullable(bacteriaReq.getRegion())
            .ifPresent(x -> Optional.ofNullable(x.getId()).ifPresent(y -> bacteria.setRegion(regionRepository.findById(y).get())));
        Optional.ofNullable(bacteriaReq.getCity())
            .ifPresent(x -> Optional.ofNullable(x.getId()).ifPresent(y -> bacteria.setCity(cityRepository.findById(y).get())));
        Optional.ofNullable(bacteriaReq.getGeolocationLat()).ifPresent(x -> bacteria.setGeolocationLat(x));
        Optional.ofNullable(bacteriaReq.getGeolocationLong()).ifPresent(x -> bacteria.setGeolocationLong(x));
        Optional.ofNullable(bacteriaReq.getDate()).ifPresent(x -> bacteria.setDate(x));
        Optional.ofNullable(bacteriaReq.getOrigin()).ifPresent(x -> Optional.ofNullable(x.getId())
            .ifPresent(y -> bacteria.setOrigin(originRepository.findById(y).get())));
        Optional.ofNullable(bacteriaReq.getSource()).ifPresent(x -> Optional.ofNullable(x.getId())
            .ifPresent(y -> bacteria.setSource(sourceRepository.findById(y).get())));
        Optional.ofNullable(bacteriaReq.getHost()).ifPresent(x -> bacteria.setHost(x));
        Optional.ofNullable(bacteriaReq.getSt()).ifPresent(x -> bacteria.setSt(x));
        Optional.ofNullable(bacteriaReq.getPlamidomes())
            .ifPresent(x -> {
                final List<Long> plasmidomeIds = x.stream().filter(y -> y.getId() != null).map(z -> z.getId()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(plasmidomeIds)) {
                    bacteria.setPlamidomes(plasmidomeRepository.findAllByIdIn(plasmidomeIds));
                }
            });
        Optional.ofNullable(bacteriaReq.getVirulomes())
            .ifPresent(x -> {
                final List<Long> virulomeIds = x.stream().filter(y -> y.getId() != null).map(z -> z.getId()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(virulomeIds)) {
                    bacteria.setVirulomes(virulomeRepository.findAllByIdIn(virulomeIds));
                }
            });

        Optional.ofNullable(bacteriaReq.getResistome()).ifPresent(x -> {
            Optional.ofNullable(x.getBLactamSet()).ifPresent(y -> bacteria.getResistome().setBLactamSet(y));
            Optional.ofNullable(x.getPhenicolSet()).ifPresent(y -> bacteria.getResistome().setPhenicolSet(y));
            Optional.ofNullable(x.getColistinSet()).ifPresent(y -> bacteria.getResistome().setColistinSet(y));
            Optional.ofNullable(x.getTetracyclineSet()).ifPresent(y -> bacteria.getResistome().setTetracyclineSet(y));
            Optional.ofNullable(x.getGlycopeptideSet()).ifPresent(y -> bacteria.getResistome().setGlycopeptideSet(y));
            Optional.ofNullable(x.getAminoglycosideSet()).ifPresent(y -> bacteria.getResistome().setAminoglycosideSet(y));
            Optional.ofNullable(x.getFosfomycinSet()).ifPresent(y -> bacteria.getResistome().setFosfomycinSet(y));
            Optional.ofNullable(x.getTrimethoprimSet()).ifPresent(y -> bacteria.getResistome().setTrimethoprimSet(y));
            Optional.ofNullable(x.getMacrolideSet()).ifPresent(y -> bacteria.getResistome().setMacrolideSet(y));
            Optional.ofNullable(x.getQuinoloneSet()).ifPresent(y -> bacteria.getResistome().setQuinoloneSet(y));
            Optional.ofNullable(x.getSulphonamideSet()).ifPresent(y -> bacteria.getResistome().setSulphonamideSet(y));
            Optional.ofNullable(x.getRifampicinSet()).ifPresent(y -> bacteria.getResistome().setRifampicinSet(y));
            Optional.ofNullable(x.getFusidicAcidSet()).ifPresent(y -> bacteria.getResistome().setFusidicAcidSet(y));
            Optional.ofNullable(x.getOxazolidinoneSet()).ifPresent(y -> bacteria.getResistome().setOxazolidinoneSet(y));
        });

        Optional.ofNullable(bacteriaReq.getKLocus()).ifPresent(x -> bacteria.setKLocus(x));
        Optional.ofNullable(bacteriaReq.getWzi()).ifPresent(x -> bacteria.setWzi(x));
        Optional.ofNullable(bacteriaReq.getWzc()).ifPresent(x -> bacteria.setWzc(x));
        Optional.ofNullable(bacteriaReq.getFimType()).ifPresent(x -> bacteria.setFimType(x));
        Optional.ofNullable(bacteriaReq.getClermontTyping()).ifPresent(x -> Optional.ofNullable(x.getId())
            .ifPresent(y -> bacteria.setClermontTyping(clermontTypingRepository.findById(y).get())));
        Optional.ofNullable(bacteriaReq.getSerotype()).ifPresent(x -> {
            Optional.ofNullable(x.getAntigenO()).ifPresent(y -> Optional.ofNullable(y.getId()).ifPresent(z -> bacteria.getSerotype().setAntigenO(
                antigenORepository.findById(z).get())));
            Optional.ofNullable(x.getAntigenH()).ifPresent(y -> Optional.ofNullable(y.getId()).ifPresent(z -> bacteria.getSerotype().setAntigenH(
                antigenHRepository.findById(z).get())));
        });

        Optional.ofNullable(bacteriaReq.getSerovar()).ifPresent(x -> Optional.ofNullable(x.getId())
            .ifPresent(y -> bacteria.setSerovar(serovarRepository.findById(y).get())));
        Optional.ofNullable(bacteriaReq.getHeavyMetal())
            .ifPresent(x -> {
                final List<Long> heavyMetalIds = x.stream().filter(y -> y.getId() != null).map(z -> z.getId()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(heavyMetalIds)) {
                    bacteria.setHeavyMetal(heavyMetalRepository.findAllByIdIn(heavyMetalIds));
                }
            });

        final Antibiogram antibiogramReq = bacteriaReq.getAntibiogram();
        final Antibiogram antibiogram = bacteria.getAntibiogram();
        if (antibiogramReq != null) {
            Optional.ofNullable(antibiogramReq.getMer()).ifPresent(x -> antibiogram.setMer(x));
            Optional.ofNullable(antibiogramReq.getEtp()).ifPresent(x -> antibiogram.setEtp(x));
            Optional.ofNullable(antibiogramReq.getIpm()).ifPresent(x -> antibiogram.setIpm(x));
            Optional.ofNullable(antibiogramReq.getCro()).ifPresent(x -> antibiogram.setCro(x));
            Optional.ofNullable(antibiogramReq.getCaz()).ifPresent(x -> antibiogram.setCaz(x));
            Optional.ofNullable(antibiogramReq.getCfx()).ifPresent(x -> antibiogram.setCfx(x));
            Optional.ofNullable(antibiogramReq.getCpm()).ifPresent(x -> antibiogram.setCpm(x));
            Optional.ofNullable(antibiogramReq.getCtx()).ifPresent(x -> antibiogram.setCtx(x));
            Optional.ofNullable(antibiogramReq.getNal()).ifPresent(x -> antibiogram.setNal(x));
            Optional.ofNullable(antibiogramReq.getCip()).ifPresent(x -> antibiogram.setCip(x));
            Optional.ofNullable(antibiogramReq.getAmc()).ifPresent(x -> antibiogram.setAmc(x));
            Optional.ofNullable(antibiogramReq.getAtm()).ifPresent(x -> antibiogram.setAtm(x));
            Optional.ofNullable(antibiogramReq.getAmi()).ifPresent(x -> antibiogram.setAmi(x));
            Optional.ofNullable(antibiogramReq.getGen()).ifPresent(x -> antibiogram.setGen(x));
            Optional.ofNullable(antibiogramReq.getSxt()).ifPresent(x -> antibiogram.setSxt(x));
            Optional.ofNullable(antibiogramReq.getEno()).ifPresent(x -> antibiogram.setEno(x));
            Optional.ofNullable(antibiogramReq.getChl()).ifPresent(x -> antibiogram.setChl(x));
            Optional.ofNullable(antibiogramReq.getFos()).ifPresent(x -> antibiogram.setFos(x));
            Optional.ofNullable(antibiogramReq.getCep()).ifPresent(x -> antibiogram.setCep(x));
            Optional.ofNullable(antibiogramReq.getCtf()).ifPresent(x -> antibiogram.setCtf(x));
            Optional.ofNullable(antibiogramReq.getAmp()).ifPresent(x -> antibiogram.setAmp(x));
            Optional.ofNullable(antibiogramReq.getTet()).ifPresent(x -> antibiogram.setTet(x));
            Optional.ofNullable(antibiogramReq.getCol()).ifPresent(x -> antibiogram.setCol(x));
        }
        Optional.ofNullable(bacteriaReq.getSequencer()).ifPresent(x -> Optional.ofNullable(x.getId())
            .ifPresent(y -> bacteria.setSequencer(sequencerRepository.findById(y).get())));
        Optional.ofNullable(bacteriaReq.getSequencingDate()).ifPresent(x -> bacteria.setSequencingDate(x));
        Optional.ofNullable(bacteriaReq.getAssembler()).ifPresent(x -> bacteria.setAssembler(x));
        Optional.ofNullable(bacteriaReq.getDateOfAssembly()).ifPresent(x -> bacteria.setDateOfAssembly(x));
        Optional.ofNullable(bacteriaReq.getGenomeBp()).ifPresent(x -> bacteria.setGenomeBp(x));
        Optional.ofNullable(bacteriaReq.getContigsNo()).ifPresent(x -> bacteria.setContigsNo(x));
        Optional.ofNullable(bacteriaReq.getAccessNoGb()).ifPresent(x -> bacteria.setAccessNoGb(x));
        Optional.ofNullable(bacteriaReq.getPaperPublished()).ifPresent(x -> bacteria.setPaperPublished(x));

        return bacteriaRepository.save(bacteria);
    }
}
