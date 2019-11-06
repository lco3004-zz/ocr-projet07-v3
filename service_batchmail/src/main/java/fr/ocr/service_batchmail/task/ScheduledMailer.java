package fr.ocr.service_batchmail.task;

import fr.ocr.service_batchmail.domain.InfosMailDtoBatch;
import fr.ocr.service_batchmail.service.BatchEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;


@Component
public class ScheduledMailer {

    private static final Logger log = LoggerFactory.getLogger(ScheduledMailer.class);

    final BatchEmailService batchEmailService;

    private final JavaMailSender javaMailSender;

    public ScheduledMailer(BatchEmailService batchEmailService, JavaMailSender javaMailSender) {
        this.batchEmailService = batchEmailService;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void emailingToOverDueBorrowers() throws  RuntimeException{
        batchEmailService.getPretHorsDelai().forEach(infosMailDtoBatch -> {
            try {
                sendEmail(infosMailDtoBatch);
            } catch (IOException e) {
                log.error("IOException levee suite appel de ScheduledMailer.sendEmail" + Arrays.toString(e.getStackTrace()));
                throw  new RuntimeException(e.getLocalizedMessage());
            } catch (MessagingException e) {
                log.error("MessagingException levee suite appel de ScheduledMailer.sendEmail" + Arrays.toString(e.getStackTrace()));
                throw  new RuntimeException(e.getLocalizedMessage());
            }
        });
    }
    void sendEmail(InfosMailDtoBatch infosMailDtoBatch) throws IOException, MessagingException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(infosMailDtoBatch.getCourriel());
        mailMessage.setSubject("Relance Usager :" + infosMailDtoBatch.getNom());
        mailMessage.setText("Bonjour, vous avez emprunte l'ouvrage :"+
                 "\n "+infosMailDtoBatch.getTitre() +
                 "\n De "+infosMailDtoBatch.getAuteur()+
                 "\n Le  "+infosMailDtoBatch.getDateEmprunt() +
                 "\n \n \n Le delai de 4 semaines est depasse." +
                 "\n Merci de nous retourner cet ouvrage "+
                 "\n L'equipe Municipale ");

        log.info(mailMessage.getText());
        javaMailSender.send(mailMessage);
    }

}