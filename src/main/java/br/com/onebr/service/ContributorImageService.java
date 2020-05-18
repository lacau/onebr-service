package br.com.onebr.service;

import br.com.onebr.controller.response.GenericImagesRes;
import br.com.onebr.controller.response.GenericImagesRes.GenericImageRes;
import br.com.onebr.controller.response.ImageRes;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.query.ContributorImageQueryResult;
import br.com.onebr.repository.ContributorImageRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class ContributorImageService {

    @Autowired
    private ContributorImageRepository contributorImageRepository;

    @Value("${app.img.path}")
    private String appImagePath;

    public GenericImagesRes findAllActive() {
        final List<ContributorImageQueryResult> contributorImages = contributorImageRepository.findAllByOrderByOrder();

        if (CollectionUtils.isEmpty(contributorImages)) {
            log.warn("message=No contributor image found on database.");
            throw new NotFoundApiException("contributor.image.not.found");
        }

        final List<GenericImageRes> members = new ArrayList<>();
        contributorImages.forEach(member -> {
            members.add(GenericImageRes.builder()
                .name(member.getName())
                .image(ImageRes.builder()
                    .id(member.getImageId())
                    .path(appImagePath)
                    .name(member.getImageName())
                    .build())
                .build());
        });

        return GenericImagesRes.builder()
            .members(members)
            .build();
    }
}
