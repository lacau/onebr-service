package br.com.onebr.service;

import br.com.onebr.controller.response.ResistomeRes;
import br.com.onebr.enumeration.ResistomeType;
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
import br.com.onebr.repository.resistome.RifampicinRepository;
import br.com.onebr.repository.resistome.SulphonamideRepository;
import br.com.onebr.repository.resistome.TetracyclineRepository;
import br.com.onebr.repository.resistome.TrimethoprimRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResistomeService {

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

    public ResistomeRes findAllToAutoComplete(ResistomeType type, String searchTerm) {
        if (type == null) {
            return ResistomeRes.builder()
                .bLactamList(bLactamRepository.findByNameLike(searchTerm))
                .phenicolList(phenicolRepository.findByNameLike(searchTerm))
                .colistinList(colistinRepository.findByNameLike(searchTerm))
                .tetracyclineList(tetracyclineRepository.findByNameLike(searchTerm))
                .glycopeptideList(glycopeptideRepository.findByNameLike(searchTerm))
                .aminoglycosideList(aminoglycosideRepository.findByNameLike(searchTerm))
                .fosfomycinList(fosfomycinRepository.findByNameLike(searchTerm))
                .trimethoprimList(trimethoprimRepository.findByNameLike(searchTerm))
                .nitroimidazoleList(nitroimidazoleRepository.findByNameLike(searchTerm))
                .macrolideList(macrolideRepository.findByNameLike(searchTerm))
                .quinoloneList(quinoloneRepository.findByNameLike(searchTerm))
                .sulphonamideList(sulphonamideRepository.findByNameLike(searchTerm))
                .rifampicinList(rifampicinRepository.findByNameLike(searchTerm))
                .fusidicAcidList(fusidicAcidRepository.findByNameLike(searchTerm))
                .oxazolidinoneList(oxazolidinoneRepository.findByNameLike(searchTerm))
                .build();
        }
        final ResistomeRes resistomeRes = ResistomeRes.builder().build();

        switch (type) {
            case B_LACTAM:
                resistomeRes.setBLactamList(bLactamRepository.findByNameLike(searchTerm));
                break;
            case PHENICOL:
                resistomeRes.setPhenicolList(phenicolRepository.findByNameLike(searchTerm));
                break;
            case COLISTIN:
                resistomeRes.setColistinList(colistinRepository.findByNameLike(searchTerm));
                break;
            case TETRACYCLINE:
                resistomeRes.setTetracyclineList(tetracyclineRepository.findByNameLike(searchTerm));
                break;
            case GLYCOPEPTIDE:
                resistomeRes.setGlycopeptideList(glycopeptideRepository.findByNameLike(searchTerm));
                break;
            case AMINOGLYCOSIDE:
                resistomeRes.setAminoglycosideList(aminoglycosideRepository.findByNameLike(searchTerm));
                break;
            case FOSFOMYCIN:
                resistomeRes.setFosfomycinList(fosfomycinRepository.findByNameLike(searchTerm));
                break;
            case TRIMETHOPRIM:
                resistomeRes.setTrimethoprimList(trimethoprimRepository.findByNameLike(searchTerm));
                break;
            case NITROIMIDAZOLE:
                resistomeRes.setNitroimidazoleList(nitroimidazoleRepository.findByNameLike(searchTerm));
                break;
            case MACROLIDE:
                resistomeRes.setMacrolideList(macrolideRepository.findByNameLike(searchTerm));
                break;
            case QUINOLONE:
                resistomeRes.setQuinoloneList(quinoloneRepository.findByNameLike(searchTerm));
                break;
            case SULPHONAMIDE:
                resistomeRes.setSulphonamideList(sulphonamideRepository.findByNameLike(searchTerm));
                break;
            case RIFAMPICIN:
                resistomeRes.setRifampicinList(rifampicinRepository.findByNameLike(searchTerm));
                break;
            case FUSIDIC_ACID:
                resistomeRes.setFusidicAcidList(fusidicAcidRepository.findByNameLike(searchTerm));
                break;
            case OXAZOLIDINONE:
                resistomeRes.setOxazolidinoneList(oxazolidinoneRepository.findByNameLike(searchTerm));
                break;
        }

        return resistomeRes;
    }
}
