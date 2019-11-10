package fr.ocr.service_batchmail.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.service_batchmail.utility.dto.InfosMailDtoBatch;
import fr.ocr.service_batchmail.utility.dto.OuvrageDtoBatch;
import fr.ocr.service_batchmail.utility.dto.PretDtoBatch;
import fr.ocr.service_batchmail.utility.dto.UserDtoBatch;
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

    void sendEmail(InfosMailDtoBatch infosMailDtoBatch) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(infosMailDtoBatch.getEmail());

        mailMessage.setSubject("Relance User :" + infosMailDtoBatch.getUserName());
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

    public List<InfosMailDtoBatch> getPretHorsDelai()
    {
        List<InfosMailDtoBatch> infosMailDtoBatchList = new ArrayList<>();

        try {
            List<PretDtoBatch> pretDtoBatchList =  listePretHorsDelai();

            for (PretDtoBatch pretDtoBatch : pretDtoBatchList) {
                InfosMailDtoBatch infosMailDtoBatch = new InfosMailDtoBatch();

                UserDtoBatch userDtoBatch = getUnfairBorrower(pretDtoBatch.getUserIduser());

                OuvrageDtoBatch ouvrageDtoBatch = getInfosOuvrage(pretDtoBatch.getOuvrageIdouvrage());

                infosMailDtoBatch.setEmail(userDtoBatch.getEmail());
                infosMailDtoBatch.setUserName(userDtoBatch.getUserName());
                infosMailDtoBatch.setDateEmprunt(pretDtoBatch.getDateEmprunt());
                infosMailDtoBatch.setTitre(ouvrageDtoBatch.getTitre());
                infosMailDtoBatch.setAuteur(ouvrageDtoBatch.getAuteur());

                infosMailDtoBatchList.add(infosMailDtoBatch);

                log.info(infosMailDtoBatch.toString());
            }

        } catch (Exception e) {
            infosMailDtoBatchList.clear();
            log.warn("Exception leve dans reception infos  : " +e.getLocalizedMessage() +e.getMessage());
        }
        return infosMailDtoBatchList;
    }


    public OuvrageDtoBatch getInfosOuvrage(int ParamsUriOuvrage ) throws Exception {

        OuvrageDtoBatch ouvrageDtoBatch =null;

        String uriOuvrageDtoById = "http://localhost:9090/OuvrageDtoByID/";
        HttpRequest request = restClient.requestBuilder(URI.create(uriOuvrageDtoById + ParamsUriOuvrage), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ouvrageDtoBatch = objectMapper.readValue(response.body(), OuvrageDtoBatch.class);
        }
        return ouvrageDtoBatch;
    }

    public List<PretDtoBatch> listePretHorsDelai() throws Exception {
        String ParamsUriPretsHorsdelai = "?currentDate=2019-11-04&elapsedWeeks=4";

        List<PretDtoBatch> pretDtoBatchList =null;

        String uriPretHorsDelai = "http://localhost:9090/ListePretsHorsDelai";
        HttpRequest request = restClient.requestBuilder(URI.create(uriPretHorsDelai + ParamsUriPretsHorsdelai), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            pretDtoBatchList = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        }
        return pretDtoBatchList;
    }


    public UserDtoBatch getUnfairBorrower(int ParamsUriIdUser ) throws Exception {

        UserDtoBatch userDtoBatch =null;

        String uriUserById = "http://localhost:9090/UserById/";
        HttpRequest request = restClient.requestBuilder(URI.create(uriUserById + ParamsUriIdUser), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            userDtoBatch = objectMapper.readValue(response.body(), UserDtoBatch.class);
        }
        return userDtoBatch;
    }

}