package fr.ocr.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.domain.PretDtoWeb;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PretService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public PretService(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public List<PretDtoWeb> getPretsByUsagerNameWithCriteria(String nomUsager) throws IOException, InterruptedException {
        List<PretDtoWeb> pretDtoWebList= null ;

        String uriPretByNomUsager = "http://localhost:9090/CriteriaListePrets/";
        HttpRequest request = restClient.requestBuilder(URI.create(uriPretByNomUsager + nomUsager), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            pretDtoWebList = objectMapper.readValue(response.body(), new TypeReference<>() {});
        }
        return pretDtoWebList;
    }

}

