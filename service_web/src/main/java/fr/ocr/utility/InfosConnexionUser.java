package fr.ocr.utility;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Value
@Component
public final class InfosConnexionUser {

    public static ResponseEntity<Map<String, Object>> formeReponseEntity(HttpServletResponse response, UserDetails userDetails) {

        Map<String, Object> stringObjectMap = new HashMap<>();

        stringObjectMap.put("userName", userDetails.getUsername());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.valueOf(response.getStatus()));
        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringObjectMap);

    }
}
