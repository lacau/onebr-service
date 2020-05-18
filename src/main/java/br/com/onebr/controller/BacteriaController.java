package br.com.onebr.controller;

import br.com.onebr.controller.request.BacteriaPatchReq;
import br.com.onebr.controller.request.BacteriaPostReq;
import br.com.onebr.controller.request.BacteriaSearchReq;
import br.com.onebr.controller.response.BacteriaViewRes;
import br.com.onebr.enumeration.BacteriaType;
import br.com.onebr.enumeration.Language;
import br.com.onebr.exception.ConflictApiException;
import br.com.onebr.model.Bacteria;
import br.com.onebr.security.OneBrConstants.ROLE;
import br.com.onebr.service.BacteriaService;
import br.com.onebr.service.util.ApiOneBr;
import br.com.onebr.service.util.RequestContextUtil;
import br.com.onebr.service.util.SecurityUtil;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
@Slf4j
public class BacteriaController {

    @Autowired
    private BacteriaService bacteriaService;

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping("/bacteria")
    public ResponseEntity<Page> getBacterias(@RequestParam("type") BacteriaType bacteriaType,
        @RequestParam(value = "searchTerm", required = false) String searchTerm,
        @RequestParam(value = "resistomes", required = false) List<Integer> resistomes,
        @RequestParam(value = "yearStart", required = false) Integer yearStart,
        @RequestParam(value = "yearEnd", required = false) Integer yearEnd,
        @RequestParam(value = "ids", required = false) List<Integer> ids,
        Pageable page) {
        Sort sort = translateSort(page.getSort());
        final BacteriaSearchReq bacteriaSearchReq = new BacteriaSearchReq(page, sort);

        bacteriaSearchReq.setBacteriaType(bacteriaType);
        bacteriaSearchReq.setSearchTerm(searchTerm);
        bacteriaSearchReq.setResistomes(resistomes);
        bacteriaSearchReq.setDate(yearStart, yearEnd);
        bacteriaSearchReq.setIds(ids);

        return ResponseEntity.ok(bacteriaService.search(bacteriaSearchReq));
    }

    @GetMapping("/bacteria/{id}")
    public ResponseEntity<BacteriaViewRes> getBacteria(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bacteriaService.findByIdForView(id, RequestContextUtil.getInstance().getLocale().getLanguage()));
    }

    @GetMapping("/admin/bacteria")
    @PreAuthorize("hasAnyAuthority('" + ROLE.USER + "','" + ROLE.ADMIN + "')")
    public ResponseEntity<Page> getBacteriasAdmin(
        @RequestParam(value = "type", required = false) BacteriaType bacteriaType,
        @RequestParam(value = "searchTerm", required = false) String searchTerm,
        @RequestParam(value = "yearStart", required = false) Integer yearStart,
        @RequestParam(value = "yearEnd", required = false) Integer yearEnd,
        Pageable page) {
        Sort sort = translateSort(page.getSort());
        final BacteriaSearchReq bacteriaSearchReq = new BacteriaSearchReq(page, sort);

        bacteriaSearchReq.setBacteriaType(bacteriaType);
        bacteriaSearchReq.setSearchTerm(searchTerm);
        bacteriaSearchReq.setDate(yearStart, yearEnd);

        return ResponseEntity.ok(bacteriaService.searchAdmin(bacteriaSearchReq));
    }

    @GetMapping("/admin/bacteria/{id}")
    @PreAuthorize("hasAnyAuthority('" + ROLE.USER + "','" + ROLE.ADMIN + "')")
    public ResponseEntity<Bacteria> getBacteriaAdmin(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bacteriaService.findByIdForEdit(id));
    }

    @DeleteMapping("/admin/bacteria/{id}")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "')")
    public ResponseEntity<Bacteria> deleteBacteriaAdmin(@PathVariable("id") Long id) {
        bacteriaService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/bacteria")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<Bacteria> postBacteria(@Valid @RequestBody BacteriaPostReq req, HttpServletRequest request) {
        final Bacteria bacteria;
        try {
            bacteria = bacteriaService.postBacteria(req);
        } catch (DataIntegrityViolationException e) {
            log.error("message=Attempt to save new experiment with used barcode and identification. barcode={}, identification={}", req.getBarcode(),
                req.getIdentification());
            throw new ConflictApiException("experiment.already.exists", req.getBarcode(), req.getIdentification());
        }

        return ResponseEntity.created(URI.create(request.getRequestURI())).body(bacteria);
    }

    @PatchMapping("/admin/bacteria/{id}")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<Bacteria> patchBacteria(@PathVariable("id") Long id, @Valid @RequestBody BacteriaPatchReq req) {
        final Bacteria bacteria = bacteriaService.patchBacteria(id, req);

        return ResponseEntity.ok(bacteria);
    }

    private Sort translateSort(Sort sort) {
        final boolean isPortuguese = Language.PT.getCode().equals(RequestContextUtil.getInstance().getLocale().getLanguage());
        final List<Order> orders = new ArrayList<>();
        for (Iterator<Order> it = sort.iterator(); it.hasNext(); ) {
            final Order next = it.next();
            if (next.getProperty().equals("origin")) {
                orders.add(getOrderByDirection(next.getDirection(), "originNamePt", "originNameEn", isPortuguese));
            } else if (next.getProperty().equals("source")) {
                orders.add(getOrderByDirection(next.getDirection(), "sourceNamePt", "sourceNameEn", isPortuguese));
            } else {
                orders.add(next);
            }
        }

        return Sort.by(orders);
    }

    private Order getOrderByDirection(Direction direction, String fieldPt, String fieldEn, boolean isPortuguese) {
        if (direction == Direction.ASC) {
            return Order.asc(isPortuguese ? fieldPt : fieldEn);
        }
        return Order.desc(isPortuguese ? fieldPt : fieldEn);
    }
}
