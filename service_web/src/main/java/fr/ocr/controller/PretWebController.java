package fr.ocr.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.model.PretWeb;
import fr.ocr.model.UserWeb;
import fr.ocr.userdetails.UserWebUserDetailsService;
import fr.ocr.utility.InfosWebRecherchePretWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Api(value = "APIs de gestion des Prets.")
@RestController
public class PretWebController {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final PrjExceptionHandler prjExceptionHandler;
    private final UserWebUserDetailsService userWebUserDetailsService;

    public PretWebController(RestClient restClient,
                             ObjectMapper objectMapper,
                             PrjExceptionHandler prjExceptionHandler,
                             UserWebUserDetailsService userWebService) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.prjExceptionHandler = prjExceptionHandler;

        this.userWebUserDetailsService = userWebService;
    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts d'un user grâce à son nom")
    @GetMapping(value="/listePrets",  produces= MediaType.APPLICATION_JSON_VALUE)
    public  List<PretWeb> getPretByNomUsagerCriteria() throws IOException, InterruptedException {

        List<PretWeb> pretWebList =null;

        String uriPretByNomUsager = "http://localhost:9090/CriteriaListePrets/";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }

        HttpRequest request = restClient.requestBuilder(URI.create(uriPretByNomUsager + username), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            pretWebList = objectMapper.readValue(response.body(), new TypeReference<>() {});
        }
        else if (response.statusCode() == HttpStatus.NOT_ACCEPTABLE.value()){
            prjExceptionHandler.throwPretNotAcceptable("Cause : Usager n'a aucun pret en cours ");

        } else if (response.statusCode() == HttpStatus.UNAUTHORIZED.value()){
            prjExceptionHandler.throwUserUnAuthorized();

        } else {
            prjExceptionHandler.throwInternalServeurError("Cause: "+ HttpStatus.valueOf(response.statusCode()));
        }

        return pretWebList;
    }

    @ApiOperation(value = "Prolonge le Pret d'un user")
    @PutMapping(value = "/prolongerPret")
    public ResponseEntity<Integer> prolongerPret(@RequestBody PretWeb pretWeb) throws IOException, InterruptedException {

        String uriOuvrageDtoById = "http://localhost:9090/ProlongerPret/";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        UserWeb userWeb = userWebUserDetailsService.getFromServiceCrud(username);

        if (pretWeb.getUserIduser() != userWeb.getIdUser())
            prjExceptionHandler.throwUserUnAuthorized();

        Map<String,Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put("idUser", pretWeb.getUserIduser());
        stringIntegerMap.put("idOuvrage",pretWeb.getOuvrageIdouvrage());

        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(stringIntegerMap);

        HttpRequest request = restClient
                .requestBuilder(URI.create(uriOuvrageDtoById), null)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();


        return  InfosWebRecherchePretWeb.formeReponseEntity(restClient.send(request));
    }
}
