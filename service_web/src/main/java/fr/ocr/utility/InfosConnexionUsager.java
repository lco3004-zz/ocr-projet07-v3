package fr.ocr.utility;

import fr.ocr.utility.dto.User;
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

    public ResponseEntity<Map<String, Object>> formeReponseEntity(HttpResponse<String> httpResponse, User user) {

        Map<String,Object> stringObjectMap = getStringStringMap(user);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.valueOf(httpResponse.statusCode()));
        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringObjectMap);

    }

    public Map<String, Object> getStringStringMap(User user) {
        Map<String,Object> stringObjectMap = new HashMap<>();

        stringObjectMap.put("idusager", user.getIdusager());
        stringObjectMap.put("nom", user.getNom());
        stringObjectMap.put("courriel", user.getCourriel());

        return stringObjectMap;
    }


}
