package fr.ocr.utility;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpResponse;

@Data
public final  class InfosWebRecherchePretWeb {

    private Integer idUser;
    private Integer idOuvrage;

    public static ResponseEntity<Integer> formeReponseEntity(HttpResponse<String> httpResponse) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(httpResponse.statusCode());
        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(httpResponse.statusCode());

    }
}