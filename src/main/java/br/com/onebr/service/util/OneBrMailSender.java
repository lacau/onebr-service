package br.com.onebr.service.util;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class OneBrMailSender {

    @Autowired
    private MailSender mailSender;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.to}")
    private String to;

    @Value("#{'${mail.bcc.to}'.split(',')}")
    private List<String> bccTo;

    public void sendEmail(String subject, String text, String replyTo, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setReplyTo(replyTo == null ? from : replyTo);
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        if (!CollectionUtils.isEmpty(bccTo)) {
            message.setBcc(bccTo.toArray(new String[0]));
        }

        mailSender.send(message);
    }

    public void sendEmail(String subject, String text, String replyTo) {
        sendEmail(subject, text, replyTo, to);
    }
}
