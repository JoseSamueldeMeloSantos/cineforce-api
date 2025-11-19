package br.com.bth8.cineforce.infra.mail;

import br.com.bth8.cineforce.config.EmailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.StringTokenizer;

@Slf4j
@Component
public class EmailSender {

    private  final JavaMailSender mailSender;
    private String to;
    private String subject;
    private String body;
    private ArrayList<InternetAddress> recipients = new ArrayList<>();//todos os e-mails

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public EmailSender to(String to) {
        this.to = to;
        this.recipients = getRecipients(to);
        return this;
    }

    public EmailSender withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailSender withMessage(String body) {
        this.body = body;
        return this;
    }

    public void send(EmailConfig config) {
        log.info("Sending email...");

        MimeMessage message = mailSender.createMimeMessage();//representa um e-mail completo

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);//O Helper -> facilita o tratamento do MineMessage

            helper.setFrom(config.getUsername());
            helper.setTo(recipients.toArray(new InternetAddress[0]));
            helper.setSubject(subject);
            helper.setText(body, true);//para dizer que tex é do tipo html

            mailSender.send(message);

            log.info("Email sent to {} with the subject {}", to, subject);

            reset();//para não ficar resto de info para o proximo envio

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void  reset() {
        this.to = null;
        this.subject = null;
        this.body = null;
        this.recipients = null;
    }



    public ArrayList<InternetAddress> getRecipients(String to) {
        String toWithoutSpaces = to.trim();
        StringTokenizer tok = new StringTokenizer(toWithoutSpaces, ";");//tokeinizando
        ArrayList<InternetAddress> recipientsList = new ArrayList<>();

        while (tok.hasMoreElements()) {
            try {
                recipientsList.add(new InternetAddress(tok.nextElement().toString()));
            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }

        return recipientsList;
    }
}
