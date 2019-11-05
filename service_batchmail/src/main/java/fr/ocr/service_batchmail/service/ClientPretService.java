package fr.ocr.service_batchmail.service;

import fr.ocr.service_batchmail.domain.PretDtoBatch;

import io.netty.handler.codec.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class ClientPretService {

    private final WebClient webClient;

    public ClientPretService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:9090").build();
    }


    public List<PretDtoBatch> listePretHorsDelai ()  {

        Mono<List<PretDtoBatch>> listMono =  webClient.get()
                .uri("/ListePretsHorsDelai/?currentDate=2019-11-04&elapsedWeeks=4")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(PretDtoBatch.class)
                .collectList();

        Mono<Boolean> j = listMono.hasElement();
        try {
            j.wait(250);
        } catch (InterruptedException e) {

        }

        return  null;
    }

}
