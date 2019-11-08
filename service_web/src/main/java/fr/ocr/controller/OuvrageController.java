package fr.ocr.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.utility.dto.OuvrageDtoWeb;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Api(value = "APIs de gestion des Ouvrages.")
@RestController
public class OuvrageController {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final PrjExceptionHandler prjExceptionHandler;

    public OuvrageController(RestClient restClient, ObjectMapper objectMapper, PrjExceptionHandler prjExceptionHandler) {
        this.restClient = restClient;

        this.objectMapper = objectMapper;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @ApiOperation(value = "Recherche d'ouvrage par titre ou par auteur")
    @PostMapping(value="/LookForOuvrage")
    public List<OuvrageDtoWeb> getOuvrageByQuery(@RequestBody(required = false) Map<String,String> criterionList) throws IOException, InterruptedException {
        List<OuvrageDtoWeb> ouvrageDtoWebList =null;

        String uriOuvrageDtoById = "http://localhost:9090/LookForOuvrage/";

        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(criterionList);

        HttpRequest request = restClient.requestBuilder(URI.create(uriOuvrageDtoById), null)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ouvrageDtoWebList = objectMapper.readValue(response.body(), new TypeReference<>(){});
        }else {
            prjExceptionHandler.throwOuvrageNotFound();
        }
        return ouvrageDtoWebList;

    }

}
