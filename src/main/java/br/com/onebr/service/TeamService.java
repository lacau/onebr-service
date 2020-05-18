package br.com.onebr.service;

import br.com.onebr.controller.response.ImageRes;
import br.com.onebr.controller.response.TeamRes;
import br.com.onebr.controller.response.TeamRes.TeamMemberRes;
import br.com.onebr.exception.NotFoundApiException;
import br.com.onebr.model.config.TeamTitle;
import br.com.onebr.model.query.TeamQueryResult;
import br.com.onebr.repository.TeamRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Value("${app.img.path}")
    private String appImagePath;

    public TeamRes findAllActive() {
        final List<TeamQueryResult> team = teamRepository.findAllByActiveIsTrueOrderByOrder();

        if (CollectionUtils.isEmpty(team)) {
            log.warn("message=No team found on database.");
            throw new NotFoundApiException("team.not.found");
        }

        final List<TeamMemberRes> members = new ArrayList<>();
        team.forEach(member -> {
            members.add(TeamMemberRes.builder()
                .order(member.getOrder())
                .title(TeamTitle.valueOf(member.getTitle()))
                .name(member.getName())
                .description(member.getDescription())
                .curriculumLink(member.getCurriculumLink())
                .image(ImageRes.builder()
                    .id(member.getImageId())
                    .path(appImagePath)
                    .name(member.getImageName())
                    .build())
                .build());
        });

        return TeamRes.builder()
            .team(members)
            .build();
    }
}
