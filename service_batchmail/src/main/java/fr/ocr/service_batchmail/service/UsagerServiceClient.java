package fr.ocr.service_batchmail.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.service_batchmail.domain.UsagerDtoBatch;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class UsagerServiceClient {

    private final ObjectMapper objectMapper;
    private final RestClient restClient = new RestClient();

    private  final String uriUsagerById = "http://localhost:9090/UsagerById/";

    public UsagerServiceClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public UsagerDtoBatch getUnfairBorrower( int ParamsUriIdUsager ) throws Exception {

        UsagerDtoBatch  usagerDtoBatch =null;

        HttpRequest request = restClient.requestBuilder(URI.create(uriUsagerById + String.valueOf(ParamsUriIdUsager)), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            usagerDtoBatch = objectMapper.readValue(response.body(), UsagerDtoBatch.class);
        }
        return usagerDtoBatch;
    }
}





