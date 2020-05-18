package br.com.onebr.service;

import br.com.onebr.controller.response.ContributorsRes;
import br.com.onebr.controller.response.ContributorsRes.ContributorRes;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.config.Contributor;
import br.com.onebr.repository.ContributorRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class ContributorService {

    @Autowired
    private ContributorRepository contributorRepository;

    public ContributorsRes findAllActive() {
        final List<Contributor> contributors = contributorRepository.findAllByActiveIsTrueOrderByOrder();

        if (CollectionUtils.isEmpty(contributors)) {
            log.warn("message=No contributor found on database.");
            throw new NotFoundApiException("contributor.not.found");
        }

        final List<ContributorRes> members = new ArrayList<>();
        contributors.forEach(member -> members.add(ContributorRes.builder()
            .order(member.getOrder())
            .name(member.getName())
            .description(member.getDescription())
            .build()));

        return ContributorsRes.builder()
            .contributors(members)
            .build();
    }
}
