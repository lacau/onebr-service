package br.com.onebr.controller;

import br.com.onebr.security.OneBrConstants.ROLE;
import br.com.onebr.service.importer.BacteriaImporterService;
import java.io.IOException;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/admin/importer", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImporterController {

    @Autowired
    private BacteriaImporterService bacteriaImporterService;

    @PostMapping
    @PreAuthorize("hasAuthority('" + ROLE.ADMIN + "')")
    @ApiIgnore
    public ResponseEntity importCsvBacteria(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        bacteriaImporterService.importBacteriaCsv(file);

        return ResponseEntity.ok().build();
    }
}
