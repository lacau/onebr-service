package br.com.onebr.service;

import br.com.onebr.controller.request.PublicationPatchReq;
import br.com.onebr.controller.request.PublicationPostReq;
import br.com.onebr.controller.response.PublicationPostRes;
import br.com.onebr.controller.response.PublicationRes;
import br.com.onebr.exception.InternalServerErrorApiException;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.config.Publication;
import br.com.onebr.model.config.PublicationType;
import br.com.onebr.model.security.User;
import br.com.onebr.repository.PublicationRepository;
import br.com.onebr.repository.UserRepository;
import br.com.onebr.security.AuthenticationRes;
import br.com.onebr.service.util.RequestContextUtil;
import br.com.onebr.service.util.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public Page<Publication> findAll(String searchTerm, Pageable page) {
        final AuthenticationRes auth = securityUtil.getAuthentication();
        if (searchTerm == null) {
            searchTerm = "";
        }
        Page<Publication> publications;
        if (auth.isAdmin()) {
            publications = publicationRepository.findAllByTitleLike(searchTerm, page);
        } else {
            publications = publicationRepository.findAllByUserIdAndTitleLike(auth.getId(), searchTerm, page);
        }

        if (publications.isEmpty()) {
            log.warn("message=No publications found on database.");
            throw new NotFoundApiException("publications.not.found");
        }

        return publications;
    }

    public Page<List<PublicationRes>> findAllActive(PublicationType publicationType, PageRequest pageRequest) {
        Page<Publication> publications;
        if (publicationType == null) {
            publications = publicationRepository.findAllByActiveIsTrueOrderByDateDescNullsLastOrderAsc(pageRequest);
        } else {
            publications = publicationRepository.findAllByActiveIsTrueWithTypeOrderByDateDescNullsLastOrderAsc(publicationType.name(), pageRequest);
        }

        if (publications.isEmpty()) {
            log.warn("message=No publications found on database.");
            throw new NotFoundApiException("publications.not.found");
        }

        final List<PublicationRes> publicationResList = new ArrayList<>();
        publications.forEach(pub -> publicationResList.add(
            new PublicationRes(RequestContextUtil.getInstance().getLocale().getLanguage())
                .build(pub)));

        return new PageImpl(publicationResList, pageRequest, publications.getTotalElements());
    }

    public Publication getPublication(Long id) {
        final Optional<Publication> publication = publicationRepository.findById(id);
        publication.ifPresentOrElse(p -> Optional.ofNullable(p.getUser()).ifPresent(u -> securityUtil.validateLoggedUserCanChangeResource(u.getId())),
            () -> {
                log.warn("message=No publication found on database. id={}", id);
                throw new NotFoundApiException("publication.not.found");
            });

        return publication.get();
    }

    @Transactional
    public PublicationPostRes postPublication(final PublicationPostReq req) {
        final AuthenticationRes auth = securityUtil.getAuthentication();
        final User user = userRepository.findById(auth.getId()).orElseThrow(() -> new InternalServerErrorApiException(""));

        Publication publication = Publication.builder()
            .type(req.getType())
            .date(req.getDate())
            .titlePt(req.getTitlePt())
            .titleEn(req.getTitleEn())
            .descriptionPt(req.getDescriptionPt())
            .descriptionEn(req.getDescriptionEn())
            .link(req.getLink())
            .user(user)
            .active(req.getActive())
            .build();

        if (auth.isAdmin()) {
            publication.setOrder(req.getOrder());
        } else {
            publication.setOrder(publicationRepository.getNextOrder());
        }

        publication = publicationRepository.save(publication);

        return new PublicationPostRes(publication.getId(),
            PublicationPostReq.builder()
                .type(publication.getType())
                .order(publication.getOrder())
                .date(publication.getDate())
                .titlePt(publication.getTitlePt())
                .titleEn(publication.getTitleEn())
                .descriptionPt(publication.getDescriptionPt())
                .descriptionEn(publication.getDescriptionEn())
                .link(publication.getLink())
                .active(publication.isActive())
                .build());
    }

    @Transactional
    public PublicationPostRes patchPublication(Long id, PublicationPatchReq req) {
        final Publication publication = publicationRepository.findById(id).orElseThrow(() -> {
            log.warn("message=Attempt to update publication with invalid id. id={}", id);
            throw new NotFoundApiException("publication.not.found");
        });

        Optional.ofNullable(publication.getUser()).ifPresent(u -> securityUtil.validateLoggedUserCanChangeResource(u.getId()));

        Optional.ofNullable(req.getType()).ifPresent(x -> publication.setType(x));
        Optional.ofNullable(req.getTitlePt()).filter(x -> !StringUtils.isEmpty(x)).ifPresent(x -> publication.setTitlePt(x));
        Optional.ofNullable(req.getTitleEn()).filter(x -> !StringUtils.isEmpty(x)).ifPresent(x -> publication.setTitleEn(x));
        Optional.ofNullable(req.getDescriptionPt()).filter(x -> !StringUtils.isEmpty(x)).ifPresent(x -> publication.setDescriptionPt(x));
        Optional.ofNullable(req.getDescriptionEn()).filter(x -> !StringUtils.isEmpty(x)).ifPresent(x -> publication.setDescriptionEn(x));
        Optional.ofNullable(req.getLink()).filter(x -> !StringUtils.isEmpty(x)).ifPresent(x -> publication.setLink(x));
        Optional.ofNullable(req.getDate()).filter(x -> !StringUtils.isEmpty(x)).ifPresent(x -> publication.setDate(x));
        Optional.ofNullable(req.getActive()).ifPresent(x -> publication.setActive(x));
        Optional.ofNullable(req.getOrder()).ifPresent(x -> {
            if (securityUtil.getAuthentication().isAdmin()) {
                publication.setOrder(x);
            }
        });

        final Publication publicationDb = publicationRepository.save(publication);

        return new PublicationPostRes(publicationDb.getId(),
            PublicationPostReq.builder()
                .type(publicationDb.getType())
                .order(publicationDb.getOrder())
                .date(publicationDb.getDate())
                .titlePt(publicationDb.getTitlePt())
                .titleEn(publicationDb.getTitleEn())
                .descriptionPt(publicationDb.getDescriptionPt())
                .descriptionEn(publicationDb.getDescriptionEn())
                .link(publicationDb.getLink())
                .active(publicationDb.isActive())
                .build());
    }

    @Transactional
    public void delete(Long id) {
        try {
            publicationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("message=Attempt to delete publication with invalid id. id={}", id);
            throw new NotFoundApiException("publication.not.found");
        }
    }
}
