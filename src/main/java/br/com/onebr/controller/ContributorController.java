package br.com.onebr.controller;

import br.com.onebr.controller.response.ContributorsRes;
import br.com.onebr.service.ContributorService;
import br.com.onebr.service.util.ApiOneBr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/contributor", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class ContributorController {

    @Autowired
    private ContributorService contributorService;

    @GetMapping
    public ResponseEntity<ContributorsRes> getContributors() {
        final ContributorsRes contributors = contributorService.findAllActive();

        return ResponseEntity.ok(contributors);
    }
}
