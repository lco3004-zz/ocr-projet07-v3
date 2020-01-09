package fr.ocr.application.pret;

import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Value
class InfosRecherchePret {
    Integer idUser;
    Integer idOuvrage;

    ResponseEntity<Map<String, Integer>> formeReponseEntity() {
        Map<String,Integer> stringIntegerMap = new HashMap<>();

        stringIntegerMap.put("idUser", idUser);
        stringIntegerMap.put("idOuvrage",idOuvrage);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).header("Content-Type", "application/json").body(stringIntegerMap);
    }
}
