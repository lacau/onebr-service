package br.com.onebr.controller;

import br.com.onebr.controller.request.MailReq;
import br.com.onebr.service.MailService;
import br.com.onebr.service.util.ApiOneBr;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mail", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOneBr
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping
    public ResponseEntity sendMail(@Valid @RequestBody MailReq mailReq) {
        mailService.sendContactMail(mailReq);

        return ResponseEntity.ok().build();
    }
}
