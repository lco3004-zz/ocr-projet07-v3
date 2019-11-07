package fr.ocr.service_batchmail.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.service_batchmail.domain.OuvrageDtoBatch;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



@Component
public class OuvrageServiceClient  {

    private final ObjectMapper objectMapper;

    private final RestClient restClient;

    private  final String uriUsagerById = "http://localhost:9090/OuvrageDtoByID/";

    public OuvrageServiceClient(ObjectMapper objectMapper, RestClient restClient) {
        this.objectMapper = objectMapper;
        this.restClient = restClient;
    }

    public OuvrageDtoBatch getInfosOuvrage(int ParamsUriOuvrage ) throws Exception {

        OuvrageDtoBatch ouvrageDtoBatch =null;

        HttpRequest request = restClient.requestBuilder(URI.create(uriUsagerById + ParamsUriOuvrage), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ouvrageDtoBatch = objectMapper.readValue(response.body(), OuvrageDtoBatch.class);
        }
        return ouvrageDtoBatch;
    }
}