package br.com.onebr.service;

import br.com.onebr.controller.response.GenericImagesRes;
import br.com.onebr.controller.response.GenericImagesRes.GenericImageRes;
import br.com.onebr.controller.response.ImageRes;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.query.SupportQueryResult;
import br.com.onebr.repository.SupportRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class SupportService {

    @Autowired
    private SupportRepository supportRepository;

    @Value("${app.img.path}")
    private String appImagePath;

    public GenericImagesRes findAllActive() {
        final List<SupportQueryResult> supports = supportRepository.findAllByActiveIsTrueOrderByOrder();

        if (CollectionUtils.isEmpty(supports)) {
            log.warn("message=No support found on database.");
            throw new NotFoundApiException("support.not.found");
        }

        final List<GenericImageRes> members = new ArrayList<>();
        supports.forEach(member -> {
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
