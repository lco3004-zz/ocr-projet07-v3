package fr.ocr.front_mvc.utility;


import fr.ocr.front_mvc.userdetails.UserWebUserDetails;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Value
@Component
public final class InfosConnexionUser {

    public static ResponseEntity<Map<String, Object>> formeReponseEntity(HttpServletResponse response,
                                                                         UserWebUserDetails userWeb,
                                                                         String id) {

        Map<String, Object> stringObjectMap = new HashMap<>();

        stringObjectMap.put("username", userWeb.getUsername());
        stringObjectMap.put("password", userWeb.getPassword());
        stringObjectMap.put("idUser",userWeb.getUserWeb().getIdUser());
        stringObjectMap.put("idSession", id);
        stringObjectMap.put("messageCnx",HttpStatus.valueOf(response.getStatus()).toString());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.
                status(HttpStatus.valueOf(response.getStatus()));

        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringObjectMap);

    }
}
