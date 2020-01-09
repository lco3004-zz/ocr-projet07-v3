/*
* controleur : Ouvrage - recherche Ouvrage par titre/auteur
* CORS : *
 */

package fr.ocr.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.model.OuvrageWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "APIs de gestion des Ouvrages.")
@RestController
@RequestMapping("/gestionOuvrages/*")
@CrossOrigin(origins= "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST}, allowCredentials = "true")
public class OuvrageWebController {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final PrjExceptionHandler prjExceptionHandler;

    public OuvrageWebController(RestClient restClient, ObjectMapper objectMapper, PrjExceptionHandler prjExceptionHandler) {
        this.restClient = restClient;

        this.objectMapper = objectMapper;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @ApiOperation(value = "Recherche d'ouvrage par titre ou par auteur")
    @GetMapping(value="/listeOuvrages")
    public List<OuvrageWeb> listeOuvrages(
            @RequestParam(value = "auteur",required = false) String auteur,
            @RequestParam(value = "titre",required = false) String titre) throws IOException, InterruptedException {

        List<OuvrageWeb> ouvrageWebList =null;

        String uriOuvrageDtoById = "http://localhost:9090/LookForOuvrage/";

        Map<String,String> criterionList = new HashMap<>();

        if (auteur != null && ! (auteur.isEmpty() || auteur.isBlank() ))
            criterionList.put("auteur",auteur);

        if (titre != null && ! (titre.isEmpty() || titre.isBlank() ))
            criterionList.put("titre",titre);

        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(criterionList);

        HttpRequest request = restClient.requestBuilder(URI.create(uriOuvrageDtoById), null)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ouvrageWebList = objectMapper.readValue(response.body(), new TypeReference<>(){});
        }else {
            prjExceptionHandler.throwOuvrageNotFound();
        }
        return ouvrageWebList;

    }

}