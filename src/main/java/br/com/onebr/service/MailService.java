package br.com.onebr.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import br.com.onebr.controller.request.MailReq;
import br.com.onebr.model.security.User;
import br.com.onebr.service.util.OneBrMailSender;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

@Service
@Slf4j
public class MailService {

    private static final String CONTACT_SUBJECT = "OneBr - Contato";

    private static final String PASSWORD_RECOVER_SUBJECT = "OneBr - Recuperação de senha";

    @Value("classpath:mail/template/contact.txt")
    private Resource contactTemplate;

    @Value("classpath:mail/template/password_recover.txt")
    private Resource passwordRecoverTemplate;

    @Autowired
    private OneBrMailSender oneBrMailSender;

    @Async
    @Transactional(propagation = Propagation.NEVER)
    public void sendContactMail(MailReq mailReq) {
        final String params[] = {mailReq.getName(), mailReq.getEmail(), mailReq.getTelephone(), mailReq.getMessage()};
        final String messageBody = createMessageBody(contactTemplate, params);
        oneBrMailSender.sendEmail(CONTACT_SUBJECT, messageBody, mailReq.getEmail());
    }

    @Async
    @Transactional(propagation = Propagation.NEVER)
    public void sendPasswordRecoverMail(User user, String password) {
        final String messageBody = createMessageBody(passwordRecoverTemplate, user.getUsername(), password);
        oneBrMailSender.sendEmail(PASSWORD_RECOVER_SUBJECT, messageBody, null, user.getProfile().getEmail());
    }

    private String createMessageBody(Resource template, String... args) {
        try (Reader reader = new InputStreamReader(template.getInputStream(), UTF_8)) {
            return String.format(FileCopyUtils.copyToString(reader), args);
        } catch (IOException e) {
            log.error("message=Error on parse contact.txt template. error={}", e.getMessage());
            throw new UncheckedIOException(e);
        }
    }
}
