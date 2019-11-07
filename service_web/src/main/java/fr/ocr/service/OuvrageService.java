/*

 */
package fr.ocr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.domain.OuvrageDtoWeb;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Laurent Cordier
 */
@Service
public class OuvrageService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    public OuvrageService(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public List<OuvrageDtoWeb> getOuvrageByQuerie(Map<String, String> requeteSearch) throws IOException, InterruptedException {

        String gTitre = requeteSearch.get("titre");
        String gAuteur = requeteSearch.get("auteur");

        List<OuvrageDtoWeb> ouvrageDtoWebList =null;

        String uriOuvrageDtoById = "http://localhost:9090/LookForOuvrage/";


        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(requeteSearch);

        HttpRequest request = restClient.requestBuilder(URI.create(uriOuvrageDtoById), null)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ouvrageDtoWebList = objectMapper.readValue(response.body(), new TypeReference<>(){});
        }
        return ouvrageDtoWebList;
    }

}
