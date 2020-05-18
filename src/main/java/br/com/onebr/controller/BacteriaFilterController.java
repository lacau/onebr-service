package br.com.onebr.controller;

import br.com.onebr.controller.response.OriginRes;
import br.com.onebr.controller.response.ResistomeRes;
import br.com.onebr.controller.response.SourceRes;
import br.com.onebr.controller.response.SpecieRes;
import br.com.onebr.enumeration.BacteriaType;
import br.com.onebr.enumeration.ResistomeType;
import br.com.onebr.model.AntigenH;
import br.com.onebr.model.AntigenO;
import br.com.onebr.model.ClermontTyping;
import br.com.onebr.model.HeavyMetal;
import br.com.onebr.model.Plasmidome;
import br.com.onebr.model.Sequencer;
import br.com.onebr.model.Serovar;
import br.com.onebr.model.Specie;
import br.com.onebr.model.Virulome;
import br.com.onebr.security.OneBrConstants.ROLE;
import br.com.onebr.service.ClermontTypingService;
import br.com.onebr.service.HeavyMetalService;
import br.com.onebr.service.OriginService;
import br.com.onebr.service.PlasmidomeService;
import br.com.onebr.service.ResistomeService;
import br.com.onebr.service.SequencerService;
import br.com.onebr.service.SerotypeService;
import br.com.onebr.service.SerovarService;
import br.com.onebr.service.SourceService;
import br.com.onebr.service.SpecieService;
import br.com.onebr.service.StService;
import br.com.onebr.service.VirulomeService;
import br.com.onebr.service.util.ApiOneBr;
import br.com.onebr.service.util.RequestContextUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bacteria/filter", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class BacteriaFilterController {

    @Autowired
    private SpecieService specieService;

    @Autowired
    private OriginService originService;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private StService stService;

    @Autowired
    private ClermontTypingService clermontTypingService;

    @Autowired
    private HeavyMetalService heavyMetalService;

    @Autowired
    private SerotypeService serotypeService;

    @Autowired
    private SerovarService serovarService;

    @Autowired
    private SequencerService sequencerService;

    @Autowired
    private VirulomeService virulomeService;

    @Autowired
    private PlasmidomeService plasmidomeService;

    @Autowired
    private ResistomeService resistomeService;

    @GetMapping("/specie")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<SpecieRes>> getSpecie() {
        final List<Specie> species = specieService.findAllByLoggedUser();

        return ResponseEntity.ok(species.stream().map(s -> SpecieRes.builder()
            .id(s.getId())
            .name(s.getName())
            .type(BacteriaType.fromId(s.getId()))
            .build()).collect(Collectors.toList())
        );
    }

    @GetMapping("/origin")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<OriginRes>> getOrigins() {
        final List<OriginRes> origins = originService.findAll(RequestContextUtil.getInstance().getLocale().getLanguage());

        return ResponseEntity.ok(origins);
    }

    @GetMapping("/source")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<SourceRes>> getSources() {
        final List<SourceRes> sources = sourceService.findAll(RequestContextUtil.getInstance().getLocale().getLanguage());

        return ResponseEntity.ok(sources);
    }

    @GetMapping("/st")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<String>> getSts() {
        return ResponseEntity.ok(stService.findAll());
    }

    @GetMapping("/clermontTyping")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<ClermontTyping>> getClermontTyping() {
        final List<ClermontTyping> clermontTypings = clermontTypingService.findAll();

        return ResponseEntity.ok(clermontTypings);
    }

    @GetMapping("/heavyMetal")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<HeavyMetal>> getHeavyMetal() {
        final List<HeavyMetal> heavyMetals = heavyMetalService.findAll();

        return ResponseEntity.ok(heavyMetals);
    }

    @GetMapping("/antigenO")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<AntigenO>> getAntigenO() {
        final List<AntigenO> antigenO = serotypeService.findAllAntigenO();

        return ResponseEntity.ok(antigenO);
    }

    @GetMapping("/antigenH")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<AntigenH>> getAntigenH() {
        final List<AntigenH> antigenH = serotypeService.findAllAntigenH();

        return ResponseEntity.ok(antigenH);
    }

    @GetMapping("/serovar")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<Serovar>> getSerovar() {
        final List<Serovar> serovars = serovarService.findAll();

        return ResponseEntity.ok(serovars);
    }

    @GetMapping("/sequencer")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<Sequencer>> getSequencer() {
        final List<Sequencer> sequencers = sequencerService.findAll();

        return ResponseEntity.ok(sequencers);
    }

    @GetMapping("/virulome")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<Virulome>> getVirulome() {
        final List<Virulome> virulomes = virulomeService.findAll();

        return ResponseEntity.ok(virulomes);
    }

    @GetMapping("/plasmidome")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<List<Plasmidome>> getPlasmidome() {
        final List<Plasmidome> plasmidomes = plasmidomeService.findAll();

        return ResponseEntity.ok(plasmidomes);
    }

    @GetMapping("/resistome")
    public ResponseEntity<ResistomeRes> getResistome(@RequestParam(value = "type", required = false) ResistomeType type,
        @RequestParam(value = "searchTerm") String searchTerm) {
        return ResponseEntity.ok(resistomeService.findAllToAutoComplete(type, searchTerm));
    }
}
