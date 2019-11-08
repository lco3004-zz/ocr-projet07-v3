package fr.ocr.utility;

import fr.ocr.utility.dto.UsagerDtoWeb;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Value
public class InfosConnexionUsager {

 Integer idusager;

 String nom;

 String mdp;

 String courriel;

    public ResponseEntity<Map<String, Object>> formeReponseEntity(HttpResponse<String> httpResponse, UsagerDtoWeb usagerDtoWeb) {

        Map<String,Object> stringObjectMap = getStringStringMap(usagerDtoWeb);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.valueOf(httpResponse.statusCode()));
        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringObjectMap);

    }

    public Map<String, Object> getStringStringMap(UsagerDtoWeb usagerDtoWeb) {
        Map<String,Object> stringObjectMap = new HashMap<>();

        stringObjectMap.put("idusager", usagerDtoWeb.getIdusager());
        stringObjectMap.put("nom",usagerDtoWeb.getNom());
        stringObjectMap.put("courriel",usagerDtoWeb.getCourriel());

        return stringObjectMap;
    }


}
