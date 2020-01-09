package fr.ocr.front_mvc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ocr.RestClient;
import fr.ocr.front_mvc.model.OuvrageWeb;
import fr.ocr.front_mvc.model.PretWeb;
import fr.ocr.front_mvc.model.UserWeb;
import fr.ocr.front_mvc.service.UserWebService;
import fr.ocr.front_mvc.userdetails.UserWebUserDetails;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@Api(value = "APIs de gestion des Prets.")
@Controller
@CrossOrigin(origins= "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST}, allowCredentials = "true")
public class DispatchController {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    private  final UserWebService userWebService;

    private  final Map<String, Object> model;
    private static final Logger log = LoggerFactory.getLogger(DispatchController.class);


    public DispatchController(RestClient restClient, ObjectMapper objectMapper, UserWebService userWebService, Map<String, Object> model){
        this.restClient = restClient;
        this.objectMapper = objectMapper;

        this.userWebService = userWebService;
        this.model = model;
    }
    /*
    =====================================================================================
     */
    @RequestMapping(value="/listeOuvrages", method = RequestMethod.GET)
    public ModelAndView getListeOuvragesCtrl(@RequestParam(required = false) String auteur,
                                             @RequestParam(required = false) String titre) throws IOException, InterruptedException {

        List<OuvrageWeb> ouvrageWebList;
        String maViewName = "ouvrages";
        model.clear();

        String uriOuvrageDtoById = "http://localhost:9090/LookForOuvrage/";

        Map<String,String> criterionList = new HashMap<>();

        if (auteur != null && ! (auteur.isEmpty() || auteur.isBlank() ))
            criterionList.put("auteur",auteur);

        if (titre != null && ! (titre.isEmpty() || titre.isBlank() ))
            criterionList.put("titre",titre);

        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(criterionList);

        HttpRequest request = restClient.requestBuilder(URI.create(uriOuvrageDtoById), null)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ouvrageWebList = objectMapper.readValue(response.body(), new TypeReference<>(){});
            model.put("ouvragesWebListe",ouvrageWebList);

        }
        else  {
            ouvrageWebList =new ArrayList<>();
            OuvrageWeb ouvrageWeb = new OuvrageWeb("Aucun Ouvrage ","disponible",0);
            ouvrageWebList.add(ouvrageWeb);
            model.put("ouvragesWebListe",ouvrageWebList);
        }
        return new ModelAndView(maViewName,model);

    }

    @RequestMapping(value="/rechercherOuvrages", method = RequestMethod.POST)
    public ModelAndView postRechercherOuvragesCtrl(OuvrageWeb ouvrageWeb)  {

        String nomAuteur = "";
        String titreOuvrage="";
        String monUrlOuvrage = "/listeOuvrages";
        Map<String,String> attributs = new HashMap<>();

        if (ouvrageWeb.getAuteur() != null && ! (ouvrageWeb.getAuteur().isEmpty() || ouvrageWeb.getAuteur().isBlank() ))
            nomAuteur = ouvrageWeb.getAuteur();

        if (ouvrageWeb.getTitre() != null && ! (ouvrageWeb.getTitre().isEmpty() || ouvrageWeb.getTitre().isBlank() ))
            titreOuvrage = ouvrageWeb.getTitre();


        if (!nomAuteur.equals("") && titreOuvrage.equals("")) {
            attributs.put("auteur",nomAuteur);
        }
        if (nomAuteur.equals("") && !titreOuvrage.equals("")) {
            attributs.put("titre",titreOuvrage);
        }
        if (!nomAuteur.equals("") && !titreOuvrage.equals("")) {
            attributs.put("auteur",nomAuteur);
            attributs.put("titre",titreOuvrage);
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(monUrlOuvrage);
        redirectView.setAttributesMap(attributs);

        return new ModelAndView(redirectView);
    }

    @RequestMapping(value="/rechercherOuvrages", method = RequestMethod.GET)
    public ModelAndView getRechercherOuvragesCtrl() {
        model.clear();
        model.put("ouvrageWeb", new OuvrageWeb());
        return new ModelAndView("recherche",model);
    }


    /*
    =========================================================================
     */

    @RequestMapping(value="/loginUser", method = RequestMethod.GET)
    public ModelAndView getLoginUserCtrl(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.clear();

        if (authentication != null ) {
            Object principal =authentication.getPrincipal();

            if ( principal instanceof UserWebUserDetails) {
                new SecurityContextLogoutHandler().logout(request,response,authentication);
                return new ModelAndView("home");
            }
        }

        model.put("userWeb", new UserWeb());
        return new ModelAndView("connexion",model);
    }
    @RequestMapping(value="/loginUser", method = RequestMethod.POST)
    public  ModelAndView postLoginUserCtrl(@Valid UserWeb userWeb, BindingResult bindingResult) {


        try {
            if (bindingResult.hasErrors()) {
                return  new ModelAndView("connexion");
            }
           if (userWebService.attemptAuthentication(userWeb) == null) {
                bindingResult.rejectValue("nom", "", "Usager/Mot de Passe Incorrect");
                return new ModelAndView("connexion");
            }
        }
        catch(RuntimeException e) {
                bindingResult.rejectValue("username", "", "Usager/Mot de Passe Incorrect");
                return new ModelAndView("connexion");
        }
        return new ModelAndView("home");
    }

    /*
    ==========================================================================================
     */
    @GetMapping(value="/listePrets")
    public ModelAndView getListePrestCtrl() throws RuntimeException, IOException, InterruptedException {

        List<PretWeb> pretWebList ;
        model.clear();

        String uriPretByNomUsager = "http://localhost:9090/CriteriaListePrets/";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assert principal instanceof UserWebUserDetails;
        UserWebUserDetails userWebUserDetails =null;
        String username = "";
        try {
             userWebUserDetails = (UserWebUserDetails)principal;
             username = ((UserWebUserDetails)principal).getUserWeb().getUsername();

        }catch (RuntimeException e) {
            log.warn("Accès aux prêts impossible car pas connecté");
            throw  new RuntimeException("Accès aux prêts impossible car pas connecté");
        }

        HttpRequest request = restClient.requestBuilder(URI.create(uriPretByNomUsager + username), null).GET().build();

        HttpResponse<String> response = restClient.send(request);

        if (response.statusCode() == HttpStatus.OK.value()) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            pretWebList = objectMapper.readValue(response.body(), new TypeReference<>() {});
        }
        else {
            pretWebList = new ArrayList<>();
            PretWeb pretWeb = new PretWeb();
            pretWeb.setAuteur("Aucun");
            pretWeb.setTitre("prét en cours");
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            pretWeb.setDateEmprunt(calendar.getTime());
        }

        model.put("pretWebList",pretWebList);
        return  new ModelAndView("prets",model);
    }

    @GetMapping(value = "/prolongerPret")
    public ModelAndView prolongerPret(@RequestParam Integer ouvrageIdouvrage) throws RuntimeException, IOException, InterruptedException {

        Map<String,Integer> stringIntegerMap = new HashMap<>();
        String uriOuvrageDtoById = "http://localhost:9090/ProlongerPret/";

        UserWebUserDetails userWebUserDetails = (UserWebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer userId = userWebUserDetails.getUserWeb().getIdUser();


        stringIntegerMap.put("idUser", userId);
        stringIntegerMap.put("idOuvrage",ouvrageIdouvrage);

        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(stringIntegerMap);

        HttpRequest request = restClient
                .requestBuilder(URI.create(uriOuvrageDtoById), null)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = restClient.send(request);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/listePrets");

        return new ModelAndView(redirectView);

    }

}
