/*
* Controlleur : CORS
* * récupère la liste des prets de l'usager connecté
* * prolonge le pret dont la clef (idouvrage,iduser) est passé en paramètre
* * CORS : *
 */

package fr.ocr.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.exception.PrjExceptionHandler;
import fr.ocr.model.PretWeb;
import fr.ocr.userdetails.UserWebUserDetails;
import fr.ocr.utility.InfosPretWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Api(value = "APIs de gestion des Prets.")
@RestController
@RequestMapping("/gestionPrets/*")
@CrossOrigin(origins= "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST}, allowCredentials = "true")
public class PretWebController {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final PrjExceptionHandler prjExceptionHandler;

    public PretWebController(RestClient restClient,
                             ObjectMapper objectMapper,
                             PrjExceptionHandler prjExceptionHandler
                             ) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.prjExceptionHandler = prjExceptionHandler;

    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts d'un user grâce à son nom")
    @GetMapping(value="/listePrets",  produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<PretWeb>> getPretByNomUsagerCriteria( HttpServletResponse httpServletResponse) throws RuntimeException, IOException, InterruptedException {

        List<PretWeb> pretWebList =null;

        String uriPretByNomUsager = "http://localhost:9090/CriteriaListePrets/";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assert principal instanceof UserWebUserDetails;
        UserWebUserDetails userWebUserDetails = (UserWebUserDetails)principal;
        String username = ((UserWebUserDetails)principal).getUserWeb().getUsername();

        HttpRequest request = restClient.requestBuilder(URI.create(uriPretByNomUsager + username), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            pretWebList = objectMapper.readValue(response.body(), new TypeReference<>() {});
        }
        else if (response.statusCode() == HttpStatus.NOT_ACCEPTABLE.value()){
            prjExceptionHandler.throwPretNotAcceptable("Cause : Usager n'a aucun pret en cours ");

        } else if (response.statusCode() == HttpStatus.UNAUTHORIZED.value()){
            prjExceptionHandler.throwUserUnAuthorized();

        } else {
            prjExceptionHandler.throwInternalServeurError("Cause: "+ HttpStatus.valueOf(response.statusCode()));
        }

        assert pretWebList != null;
        return InfosPretWeb.formeReponseEntity(httpServletResponse,pretWebList);
    }

    @ApiOperation(value = "Prolonge le Pret d'un user")
    @PutMapping(value = "/prolongerPret")
    public  ResponseEntity<Map<String,Object>> prolongerPret(@RequestBody PretWeb pretWeb,HttpServletResponse httpServletResponse) throws RuntimeException, IOException, InterruptedException {

        Map<String,Integer> stringIntegerMap = new HashMap<>();
        String uriOuvrageDtoById = "http://localhost:9090/ProlongerPret/";

        UserWebUserDetails userWebUserDetails = (UserWebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer userId = userWebUserDetails.getUserWeb().getIdUser();

        stringIntegerMap.put("idUser", userId);
        stringIntegerMap.put("idOuvrage",pretWeb.getOuvrageIdouvrage());

        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(stringIntegerMap);

        HttpRequest request = restClient
                .requestBuilder(URI.create(uriOuvrageDtoById), null)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.CREATED.value() || response.statusCode() == HttpStatus.OK.value() ) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            pretWeb = objectMapper.readValue(response.body(), PretWeb.class);
        }
        else if (response.statusCode() == HttpStatus.NOT_ACCEPTABLE.value()){
            prjExceptionHandler.throwPretNotAcceptable("Cause : Usager n'a aucun pret en cours ");

        } else if (response.statusCode() == HttpStatus.UNAUTHORIZED.value()) {
            prjExceptionHandler.throwUserUnAuthorized();

        } else if (response.statusCode() == HttpStatus.ALREADY_REPORTED.value()) {
            prjExceptionHandler.throwProlongationAlreadyReported("ce prêt a déja été renouvelé ou est hors-délai");

        } else {
            prjExceptionHandler.throwInternalServeurError("Cause: "+ HttpStatus.valueOf(response.statusCode()));
        }
        return InfosPretWeb.formeReponseEntity(httpServletResponse,pretWeb);
    }
}
