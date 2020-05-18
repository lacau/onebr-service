package br.com.onebr.controller;

import br.com.onebr.controller.response.GenericImagesRes;
import br.com.onebr.controller.response.ImageRes;
import br.com.onebr.security.OneBrConstants.ROLE;
import br.com.onebr.service.ContributorImageService;
import br.com.onebr.service.ImageService;
import br.com.onebr.service.SupportService;
import br.com.onebr.service.util.ApiOneBr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/image", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private SupportService supportService;

    @Autowired
    private ContributorImageService contributorImageService;

    @PostMapping
    @PreAuthorize("hasAuthority('" + ROLE.ADMIN + "')")
    @ApiIgnore
    public ResponseEntity<ImageRes> saveImage(@RequestParam("file") MultipartFile file) {
        final ImageRes imageRes = imageService.saveImage(file);

        return ResponseEntity.ok(imageRes);
    }

    @GetMapping("/support")
    public ResponseEntity<GenericImagesRes> getSupportImages() {
        final GenericImagesRes supportsRes = supportService.findAllActive();

        return ResponseEntity.ok(supportsRes);
    }

    @GetMapping("/contributor")
    public ResponseEntity<GenericImagesRes> getContributorImages() {
        final GenericImagesRes genericImagesRes = contributorImageService.findAllActive();

        return ResponseEntity.ok(genericImagesRes);
    }
}
