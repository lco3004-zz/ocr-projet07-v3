package fr.ocr.user;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Api(value = "APIs de gestion de users.")
@RestController
public class UserWebController {

    @PostMapping(value = "/cnxLogin")
    public ResponseEntity<Map<String, Object>> ConnexionUser(@RequestBody InfosConnexionUser infosConnexionUser) {

          return null;
        //return infosConnexionUser.formeReponseEntity(response, userWebDtoWeb);

    }

}
