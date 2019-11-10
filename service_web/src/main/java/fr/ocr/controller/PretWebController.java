package fr.ocr.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.utility.InfosRecherchePret;
import fr.ocr.utility.dto.PretWebDtoWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;



@Api(value = "APIs de gestion des Prets.")
@RestController
public class PretWebController {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final PrjExceptionHandler prjExceptionHandler;

    public PretWebController(RestClient restClient, ObjectMapper objectMapper, PrjExceptionHandler prjExceptionHandler) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts d'un user grâce à son nom")
    @GetMapping(value="/CriteriaListePrets/{nomUsager}",  produces= MediaType.APPLICATION_JSON_VALUE)
    public  List<PretWebDtoWeb> getPretByNomUsagerCriteria(@PathVariable String nomUsager) throws IOException, InterruptedException {

        List<PretWebDtoWeb> pretWebDtoWebList =null;

        String uriPretByNomUsager = "http://localhost:9090/CriteriaListePrets/";
        HttpRequest request = restClient.requestBuilder(URI.create(uriPretByNomUsager + nomUsager), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            pretWebDtoWebList = objectMapper.readValue(response.body(), new TypeReference<>() {});
        }
        else if (response.statusCode() == HttpStatus.NOT_ACCEPTABLE.value()){
            prjExceptionHandler.throwPretNotAcceptable("Cause : Usager n'a aucun pret en cours ");

        } else if (response.statusCode() == HttpStatus.UNAUTHORIZED.value()){
            prjExceptionHandler.throwUserUnAuthorized();

        } else {
            prjExceptionHandler.throwInternalServeurError("Cause: "+ HttpStatus.valueOf(response.statusCode()));
        }

        return pretWebDtoWebList;
    }

    @ApiOperation(value = "Prolonge le Pret d'un user")
    @PutMapping(value = "/ProlongerPret")
    public ResponseEntity<Map<String, Integer>> prolongerPret(@RequestBody InfosRecherchePret infosRecherchePret) throws IOException, InterruptedException {

        String uriOuvrageDtoById = "http://localhost:9090/ProlongerPret/";

        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(infosRecherchePret.getStringIntegerMap());

        HttpRequest request = restClient
                .requestBuilder(URI.create(uriOuvrageDtoById), null)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return infosRecherchePret.formeReponseEntity( restClient.send(request));
    }
}
