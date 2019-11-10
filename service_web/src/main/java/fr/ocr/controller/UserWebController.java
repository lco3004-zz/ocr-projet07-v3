package fr.ocr.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.security.User;
import fr.ocr.service.SecurityWebService;
import fr.ocr.utility.InfosConnexionUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


@Api(value = "APIs de gestion de users.")
@RestController
public class UserWebController {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final PrjExceptionHandler prjExceptionHandler;

    private final SecurityWebService securityWebService;

    public UserWebController(RestClient restClient, ObjectMapper objectMapper, PrjExceptionHandler prjExceptionHandler, SecurityWebService securityWebService) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.prjExceptionHandler = prjExceptionHandler;
        this.securityWebService = securityWebService;
    }


    @ApiOperation(value = "Restitution d'un Pret : un Ouvrage/un User")
    @PostMapping(value = "/cnxLogin")
    public ResponseEntity<Map<String, Object>> ConnexionUser(@RequestBody InfosConnexionUser infosConnexionUser) throws IOException, InterruptedException {

        User user = null;
        String uriUserByName = "http://localhost:9090/UserByName/"+ infosConnexionUser.getUserName();

        HttpRequest request = restClient
                .requestBuilder(URI.create(uriUserByName), null)
                .GET()
                .build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() != HttpStatus.OK.value()) {
            prjExceptionHandler.throwUserUnAuthorized();
        } else {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            user = objectMapper.readValue(response.body(), User.class);

            Boolean valRet = securityWebService.acLogin(user, infosConnexionUser);

            if (!valRet)
                prjExceptionHandler.throwUserUnAuthorized();
        }
        return infosConnexionUser.formeReponseEntity(response, user);

    }

}
