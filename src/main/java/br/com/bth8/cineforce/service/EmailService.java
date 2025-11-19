package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.config.EmailConfig;
import br.com.bth8.cineforce.infra.mail.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private EmailSender sender;
    @Autowired
    private EmailConfig config;

    public void sendSimpleEmail(
            String to,String subject, String body) {
        sender.to(to)
                .withSubject(subject)
                .withMessage(body)
                .send(new EmailConfig());
    }
}
