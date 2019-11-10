package fr.ocr.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.service.SecurityService;
import fr.ocr.utility.InfosConnexionUsager;
import fr.ocr.utility.dto.User;
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


@Api(value = "APIs de gestion de usagers.")
@RestController
public class UserWebController {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final PrjExceptionHandler prjExceptionHandler;

    private final SecurityService securityService;

    public UserWebController(RestClient restClient, ObjectMapper objectMapper, PrjExceptionHandler prjExceptionHandler, SecurityService securityService) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.prjExceptionHandler = prjExceptionHandler;
        this.securityService = securityService;
    }


    @ApiOperation(value = "Restitution d'un Pret : un Ouvrage/un Usager")
    @PostMapping(value = "/cnxLogin")
    public ResponseEntity<Map<String, Object>> ConnexionUsager(@RequestBody InfosConnexionUsager infosConnexionUsager) throws IOException, InterruptedException {

        User user = null;
        String uriUsagerByName = "http://localhost:9090/UsagerByNom/"+infosConnexionUsager.getNom();

        HttpRequest request = restClient
                .requestBuilder(URI.create(uriUsagerByName), null)
                .GET()
                .build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() != HttpStatus.OK.value()) {
            prjExceptionHandler.throwUsagerUnAuthorized();
        } else {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            user = objectMapper.readValue(response.body(), User.class);

            Boolean valRet = securityService.acLogin(user, infosConnexionUsager);

            if (!valRet)
                prjExceptionHandler.throwUsagerUnAuthorized();
        }
        return infosConnexionUsager.formeReponseEntity(response, user);

    }

}
