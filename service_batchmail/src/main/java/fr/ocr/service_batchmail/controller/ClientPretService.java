package fr.ocr.service_batchmail.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import fr.ocr.service_batchmail.domain.PretDtoBatch;
import fr.ocr.service_batchmail.util.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ClientPretService {
    @Autowired
    private ObjectMapper objectMapper;

    private final RestClient restClient = new RestClient();
    private  final String uriPretHorsDelai = "http://localhost:9090/ListePretsHorsDelai";

    public List<PretDtoBatch>  listePretHorsDelai() throws Exception {
        String ParamsUriPretsHorsdelai = "?currentDate=2019-11-04&elapsedWeeks=4";

        List<PretDtoBatch> pretDtoBatchList =null;

        HttpRequest request = restClient.requestBuilder(URI.create(uriPretHorsDelai + ParamsUriPretsHorsdelai), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
             pretDtoBatchList = objectMapper.readValue(response.body(), new TypeReference<List<PretDtoBatch>>() {});
        }
        return pretDtoBatchList;
    }
}




