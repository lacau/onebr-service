package br.com.onebr.controller;

import br.com.onebr.controller.response.TeamRes;
import br.com.onebr.service.TeamService;
import br.com.onebr.service.util.ApiOneBr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/team", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<TeamRes> getTeam() {
        final TeamRes team = teamService.findAllActive();

        return ResponseEntity.ok(team);
    }
}
