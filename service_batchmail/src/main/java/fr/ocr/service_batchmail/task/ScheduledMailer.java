package fr.ocr.service_batchmail.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.service_batchmail.utility.dto.InfosBatchMailDtoBatch;
import fr.ocr.service_batchmail.utility.dto.OuvrageBatchDtoBatch;
import fr.ocr.service_batchmail.utility.dto.PretBatchDtoBatch;
import fr.ocr.service_batchmail.utility.dto.UserBatchDtoBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Component
public class ScheduledMailer {

    private static final Logger log = LoggerFactory.getLogger(ScheduledMailer.class);


    private final JavaMailSender javaMailSender;

    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    public ScheduledMailer( JavaMailSender javaMailSender, ObjectMapper objectMapper, RestClient restClient) {
        this.javaMailSender = javaMailSender;
        this.objectMapper = objectMapper;
        this.restClient = restClient;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void emailingToOverDueBorrowers() throws  RuntimeException{
        getPretHorsDelai().forEach(this::sendEmail);
    }

    void sendEmail(InfosBatchMailDtoBatch infosBatchMailDtoBatch) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(infosBatchMailDtoBatch.getEmail());

        mailMessage.setSubject("Relance User :" + infosBatchMailDtoBatch.getUserName());
        mailMessage.setText("\n*********************************************\n"+
                 "\n  Bonjour, vous avez emprunté l'ouvrage :"+
                 "\n  Titre..: " + infosBatchMailDtoBatch.getTitre() +
                 "\n  De.....: " + infosBatchMailDtoBatch.getAuteur()+
                 "\n  Le.....: " + infosBatchMailDtoBatch.getDateEmprunt() + "\n"+
                 "\n   Le délai de 4 semaines est dépassé." +
                 "\n   Merci de nous retourner cet ouvrage "+
                 "\n L'equipe Municipale"+
                 "\n*********************************************\n");

        log.info(mailMessage.getText());
        javaMailSender.send(mailMessage);
    }

    public List<InfosBatchMailDtoBatch> getPretHorsDelai()
    {
        List<InfosBatchMailDtoBatch> infosBatchMailDtoBatchList = new ArrayList<>();

        try {
            List<PretBatchDtoBatch> pretBatchDtoBatchList =  listePretHorsDelai();

            for (PretBatchDtoBatch pretBatchDtoBatch : pretBatchDtoBatchList) {
                InfosBatchMailDtoBatch infosBatchMailDtoBatch = new InfosBatchMailDtoBatch();

                UserBatchDtoBatch userBatchDtoBatch = getUnfairBorrower(pretBatchDtoBatch.getUserIduser());

                OuvrageBatchDtoBatch ouvrageBatchDtoBatch = getInfosOuvrage(pretBatchDtoBatch.getOuvrageIdouvrage());

                infosBatchMailDtoBatch.setEmail(userBatchDtoBatch.getEmail());
                infosBatchMailDtoBatch.setUserName(userBatchDtoBatch.getUserName());
                infosBatchMailDtoBatch.setDateEmprunt(pretBatchDtoBatch.getDateEmprunt());
                infosBatchMailDtoBatch.setTitre(ouvrageBatchDtoBatch.getTitre());
                infosBatchMailDtoBatch.setAuteur(ouvrageBatchDtoBatch.getAuteur());

                infosBatchMailDtoBatchList.add(infosBatchMailDtoBatch);

                log.info(infosBatchMailDtoBatch.toString());
            }

        } catch (Exception e) {
            infosBatchMailDtoBatchList.clear();
            log.warn("Exception leve dans reception infos  : " +e.getLocalizedMessage() +e.getMessage());
        }
        return infosBatchMailDtoBatchList;
    }


    public OuvrageBatchDtoBatch getInfosOuvrage(int ParamsUriOuvrage ) throws Exception {

        OuvrageBatchDtoBatch ouvrageBatchDtoBatch =null;

        String uriOuvrageDtoById = "http://localhost:9090/OuvrageDtoByID/";
        HttpRequest request = restClient.requestBuilder(URI.create(uriOuvrageDtoById + ParamsUriOuvrage), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ouvrageBatchDtoBatch = objectMapper.readValue(response.body(), OuvrageBatchDtoBatch.class);
        }
        return ouvrageBatchDtoBatch;
    }

    public List<PretBatchDtoBatch> listePretHorsDelai() throws Exception {
        String ParamsUriPretsHorsdelai = "?currentDate=2019-11-04&elapsedWeeks=4";

        List<PretBatchDtoBatch> pretBatchDtoBatchList =null;

        String uriPretHorsDelai = "http://localhost:9090/ListePretsHorsDelai";
        HttpRequest request = restClient.requestBuilder(URI.create(uriPretHorsDelai + ParamsUriPretsHorsdelai), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            pretBatchDtoBatchList = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        }
        return pretBatchDtoBatchList;
    }


    public UserBatchDtoBatch getUnfairBorrower(int ParamsUriIdUser ) throws Exception {

        UserBatchDtoBatch userBatchDtoBatch =null;

        String uriUserById = "http://localhost:9090/UserById/";
        HttpRequest request = restClient.requestBuilder(URI.create(uriUserById + ParamsUriIdUser), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            userBatchDtoBatch = objectMapper.readValue(response.body(), UserBatchDtoBatch.class);
        }
        return userBatchDtoBatch;
    }

}