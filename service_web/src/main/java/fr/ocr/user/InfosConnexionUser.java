package fr.ocr.user;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Value
public class InfosConnexionUser {

 Integer idUser;

 String username;

 String password;

 String email;

    public ResponseEntity<Map<String, Object>> formeReponseEntity(HttpResponse<String> httpResponse, UserWebDtoWeb userWebDtoWeb) {

        Map<String,Object> stringObjectMap = getStringStringMap(userWebDtoWeb);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.valueOf(httpResponse.statusCode()));
        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringObjectMap);

    }

    public Map<String, Object> getStringStringMap(UserWebDtoWeb userWebDtoWeb) {
        Map<String,Object> stringObjectMap = new HashMap<>();

        stringObjectMap.put("idUser", userWebDtoWeb.getIdUser());
        stringObjectMap.put("userName", userWebDtoWeb.getUsername());
        stringObjectMap.put("email", userWebDtoWeb.getEmail());

        return stringObjectMap;
    }

}
