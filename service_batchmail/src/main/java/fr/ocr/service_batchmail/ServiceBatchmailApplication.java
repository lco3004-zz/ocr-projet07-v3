package fr.ocr.service_batchmail;

import fr.ocr.RestClient;
import fr.ocr.service_batchmail.task.ScheduledMailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
    mvn spring-boot:run -Dspring-boot.run.arguments="immediat"
 */

@SpringBootApplication
@EnableScheduling
public class ServiceBatchmailApplication  implements  CommandLineRunner{

    @Autowired
    private ScheduledMailer scheduledMailer;

    private static final Logger log = LoggerFactory.getLogger(ServiceBatchmailApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(ServiceBatchmailApplication.class, args);

    }

    @Bean
    public RestClient libHttpClient() {
        return new RestClient() ;
    }

    @Override
    public void run(String... args)  {
        if (args.length >0)
            try {
                scheduledMailer.emailingToOverDueBorrowers();

            }catch (RuntimeException e) {
                log.error("Exception levee suite appel de scheduledMailer.emailingToOverDueBorrowers()" + e.getLocalizedMessage());
            }
    }
}
