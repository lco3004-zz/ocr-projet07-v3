package fr.ocr.service_batchmail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.mail.MessagingException;
import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class ServiceBatchmailApplication  implements  CommandLineRunner{

    public static void main(String[] args) {

        SpringApplication.run(ServiceBatchmailApplication.class, args);

    }


    @Override
    public void run(String... args) throws IOException, MessagingException {
        sendEmail();
    }
}
