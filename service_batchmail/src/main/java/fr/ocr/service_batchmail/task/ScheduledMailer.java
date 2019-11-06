package fr.ocr.service_batchmail.task;

import fr.ocr.service_batchmail.domain.InfosMailDtoBatch;
import fr.ocr.service_batchmail.service.BatchEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ScheduledMailer {

    private static final Logger log = LoggerFactory.getLogger(ScheduledMailer.class);

    final BatchEmailService batchEmailService;

    public ScheduledMailer(BatchEmailService batchEmailService) {
        this.batchEmailService = batchEmailService;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void emailingToOverDueBorrowers() {
        batchEmailService.getPretHorsDelai().forEach( x-> {
            sendEmail(x.getCourriel());
        });

    }
    void sendEmail(String email) {

    }
}