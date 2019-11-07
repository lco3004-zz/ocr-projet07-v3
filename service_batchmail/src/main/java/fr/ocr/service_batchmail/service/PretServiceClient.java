package fr.ocr.service_batchmail.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.service_batchmail.domain.PretDtoBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Component
public class PretServiceClient {

    private final ObjectMapper objectMapper;

    private final RestClient restClient ;

    public PretServiceClient(ObjectMapper objectMapper, RestClient restClient) {
        this.objectMapper = objectMapper;
        this.restClient = restClient;
    }

    public List<PretDtoBatch>  listePretHorsDelai() throws Exception {
        String ParamsUriPretsHorsdelai = "?currentDate=2019-11-04&elapsedWeeks=4";

        List<PretDtoBatch> pretDtoBatchList =null;

        String uriPretHorsDelai = "http://localhost:9090/ListePretsHorsDelai";
        HttpRequest request = restClient.requestBuilder(URI.create(uriPretHorsDelai + ParamsUriPretsHorsdelai), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
             pretDtoBatchList = objectMapper.readValue(response.body(), new TypeReference<>() {
             });
        }
        return pretDtoBatchList;
    }
}




