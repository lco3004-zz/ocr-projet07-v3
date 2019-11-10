package fr.ocr.utility;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Value
public class InfosRecherchePret {
    Integer idUser;
    Integer idOuvrage;

    public ResponseEntity<Map<String, Integer>> formeReponseEntity(HttpResponse<String> httpResponse) {

        Map<String,Integer> stringIntegerMap = getStringIntegerMap();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.valueOf(httpResponse.statusCode()));
        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringIntegerMap);

    }

    public Map<String, Integer> getStringIntegerMap() {
        Map<String,Integer> stringIntegerMap = new HashMap<>();

        stringIntegerMap.put("idUser", idUser);
        stringIntegerMap.put("idOuvrage",idOuvrage);

        return stringIntegerMap;
    }

}