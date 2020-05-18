package br.com.onebr.controller;

import br.com.onebr.controller.request.PublicationPatchReq;
import br.com.onebr.controller.request.PublicationPostReq;
import br.com.onebr.controller.response.PublicationPostRes;
import br.com.onebr.controller.response.PublicationRes;
import br.com.onebr.enumeration.Language;
import br.com.onebr.model.config.Publication;
import br.com.onebr.model.config.PublicationType;
import br.com.onebr.security.OneBrConstants.ROLE;
import br.com.onebr.service.PublicationService;
import br.com.onebr.service.util.ApiOneBr;
import br.com.onebr.service.util.RequestContextUtil;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @GetMapping("/publication")
    public Page<List<PublicationRes>> getPublications(
        @RequestParam(name = "type", required = false) PublicationType publicationType,
        @RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
        @RequestParam(name = "size", defaultValue = "4") Integer size) {
        final Page<List<PublicationRes>> publications = publicationService.findAllActive(publicationType, PageRequest.of(pageNumber, size));

        return publications;
    }

    @GetMapping("/admin/publication")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public Page<Publication> getAdminPublications(@RequestParam(value = "searchTerm", required = false) String searchTerm, Pageable page) {
        final Sort sort = translateSort(page.getSort());

        return publicationService.findAll(searchTerm, PageRequest.of(page.getPageNumber(), page.getPageSize(), sort));
    }

    @GetMapping("/admin/publication/{id}")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<Publication> getAdminPublication(@PathVariable("id") Long id) {
        return ResponseEntity.ok(publicationService.getPublication(id));
    }

    @PostMapping("/admin/publication")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<PublicationPostRes> postPublication(@Valid @RequestBody PublicationPostReq req, HttpServletRequest request) {
        final PublicationPostRes publicationPostRes = publicationService.postPublication(req);

        return ResponseEntity.created(URI.create(request.getRequestURI())).body(publicationPostRes);
    }

    @PatchMapping("/admin/publication/{id}")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity<PublicationPostRes> patchPublication(@PathVariable("id") Long id, @Valid @RequestBody PublicationPatchReq req) {
        final PublicationPostRes publicationPostRes = publicationService.patchPublication(id, req);

        return ResponseEntity.ok(publicationPostRes);
    }

    @DeleteMapping("/admin/publication/{id}")
    @PreAuthorize("hasAnyAuthority('" + ROLE.ADMIN + "','" + ROLE.USER + "')")
    public ResponseEntity deletePublication(@PathVariable("id") Long id) {
        publicationService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private Sort translateSort(Sort sort) {
        final boolean isPortuguese = Language.PT.getCode().equals(RequestContextUtil.getInstance().getLocale().getLanguage());
        final List<Order> orders = new ArrayList<>();
        for (Iterator<Order> it = sort.iterator(); it.hasNext(); ) {
            final Order next = it.next();
            if (next.getProperty().equals("title")) {
                orders.add(getOrderByDirection(next.getDirection(), "title_pt", "title_en", isPortuguese));
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
