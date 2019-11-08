package fr.ocr.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.domain.PretDtoWeb;
import fr.ocr.service.PretService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
class InfosRecherchePret {
    Integer idUsager;
    Integer idOuvrage;

    ResponseEntity<Map<String, Integer>> formeReponseEntity(HttpResponse<String> httpResponse) {
        Map<String,Integer> stringIntegerMap = new HashMap<>();

        stringIntegerMap.put("idUsager",idUsager);
        stringIntegerMap.put("idOuvrage",idOuvrage);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.valueOf(httpResponse.statusCode()));
        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringIntegerMap);

    }

    Map<String, Integer> getStringIntegerMap() {
        Map<String,Integer> stringIntegerMap = new HashMap<>();

        stringIntegerMap.put("idUsager",idUsager);
        stringIntegerMap.put("idOuvrage",idOuvrage);

        return stringIntegerMap;
    }

}

@Api(value = "APIs de gestion des Prets.")
@RestController
public class PretController {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    final PretService pretService;

    public PretController(RestClient restClient, ObjectMapper objectMapper, PretService pretService) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.pretService = pretService;
    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts d'un usager grâce à son nom")
    @GetMapping(value="/CriteriaListePrets/{nomUsager}",  produces= MediaType.APPLICATION_JSON_VALUE)
    public  List<PretDtoWeb> getPretByNomUsagerCriteria(@PathVariable String nomUsager) throws IOException, InterruptedException {
        return pretService.getPretsByUsagerNameWithCriteria(nomUsager);
    }

    @ApiOperation(value = "Prolonge le Pret d'un usager")
    @PutMapping(value = "/ProlongerPret")
    public ResponseEntity<Map<String, Integer>> prolongerPret(@RequestBody  InfosRecherchePret infosRecherchePret) throws IOException, InterruptedException {


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
