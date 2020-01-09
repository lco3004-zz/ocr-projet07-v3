
/*
* Composant :
* * récupére la liste des prets hors délai et les traite
* * Crontab Like : envoie mail vers usagers ayant au moins un  pret hors delais
 */

package fr.ocr.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.model.InfosBatchMailDtoBatch;
import fr.ocr.model.OuvrageBatchDtoBatch;
import fr.ocr.model.PretBatchDtoBatch;
import fr.ocr.model.UserBatchDtoBatch;
import fr.ocr.utility.ApplicationPropertiesConfiguration;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Component
public class ScheduledMailer {

    private static final Logger log = LoggerFactory.getLogger(ScheduledMailer.class);


    private final JavaMailSender javaMailSender;

    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    private  final ApplicationPropertiesConfiguration appProperties;


    public ScheduledMailer(JavaMailSender javaMailSender,
                           ObjectMapper objectMapper,
                           RestClient restClient,
                           ApplicationPropertiesConfiguration appProperties) {
        this.javaMailSender = javaMailSender;
        this.objectMapper = objectMapper;
        this.restClient = restClient;
        this.appProperties = appProperties;
    }

    //CRONTAB like - tous les jours à 10h.
    @Scheduled(cron = "0 0 10 * * *")
    public void emailingToOverDueBorrowers() throws  RuntimeException{
        getPretHorsDelai().forEach(this::sendEmail);
    }

    //émetteur mail pour un usager
    void sendEmail(InfosBatchMailDtoBatch infosBatchMailDtoBatch) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(infosBatchMailDtoBatch.getEmail());

        mailMessage.setSubject("Relance User :" + infosBatchMailDtoBatch.getUsername());
        mailMessage.setText("\n*********************************************\n"+
                 "\n  Bonjour, vous avez emprunté l'ouvrage :"+
                 "\n  Titre..: " + infosBatchMailDtoBatch.getTitre() +
                 "\n  De.....: " + infosBatchMailDtoBatch.getAuteur()+
                 "\n  Le.....: " + infosBatchMailDtoBatch.getDateEmprunt() + "\n"+
                 "\n   Le délai de "+appProperties.getDureePret() + " semaines est dépassé." +
                 "\n   Merci de nous retourner cet ouvrage "+
                 "\n L'equipe Municipale"+
                 "\n*********************************************\n");

        log.info(mailMessage.getText());
        try {
            javaMailSender.send(mailMessage);
        }catch (Exception e ) {
            log.warn("Erreur : javalMail to "+ mailMessage.getTo()+ " - cause :" + e.getLocalizedMessage());
        }
    }

    //fabrique la liste détaillée des infos liées aux prets hors delais
    //en récuperant les prets hors delais puis  les usagers et les ouvrages reliés à ces prets
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
                infosBatchMailDtoBatch.setUsername(userBatchDtoBatch.getUsername());
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

    //récupere le infos de l'ouvrage dont l'id est passé en param
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

    //récupère la liste des prets hors-délais selon date du jour - 4 semaines
    public List<PretBatchDtoBatch> listePretHorsDelai() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String ParamsUriPretsHorsdelai = "?currentDate="+
                dateFormat.format(Calendar.getInstance().getTime())+
                "&elapsedWeeks="+ appProperties.getDureePret();

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

    //récupere les infos de l'usager dont l'id est passé en param
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