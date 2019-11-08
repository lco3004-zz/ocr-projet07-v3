package fr.ocr.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.utility.InfosRecherchePret;
import fr.ocr.utility.dto.PretDtoWeb;
import fr.ocr.utility.exception.OperationImpossibleException;
import fr.ocr.utility.exception.PretNotFoundException;
import fr.ocr.utility.exception.UsagerNotFoundException;
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
public class PretController {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public PretController(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts d'un usager grâce à son nom")
    @GetMapping(value="/CriteriaListePrets/{nomUsager}",  produces= MediaType.APPLICATION_JSON_VALUE)
    public  List<PretDtoWeb> getPretByNomUsagerCriteria(@PathVariable String nomUsager) throws IOException, InterruptedException {

        List<PretDtoWeb> pretDtoWebList ;

        String uriPretByNomUsager = "http://localhost:9090/CriteriaListePrets/";
        HttpRequest request = restClient.requestBuilder(URI.create(uriPretByNomUsager + nomUsager), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            pretDtoWebList = objectMapper.readValue(response.body(), new TypeReference<>() {});
        }
        else if (response.statusCode() == HttpStatus.NOT_FOUND.value()){
            throw new PretNotFoundException("Cause : Usager n'a aucun pret en cours ");

        } else if (response.statusCode() == HttpStatus.EXPECTATION_FAILED.value()){
            throw  new UsagerNotFoundException("Cause: Usager inconnu ");

        } else {
            throw  new  OperationImpossibleException("Cause: "+ HttpStatus.valueOf(response.statusCode()));
        }

        return pretDtoWebList;
    }

    @ApiOperation(value = "Prolonge le Pret d'un usager")
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
