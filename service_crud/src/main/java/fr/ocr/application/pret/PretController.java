package fr.ocr.application.pret;

import fr.ocr.application.ouvrage.OuvrageService;
import fr.ocr.application.user.UserService;
import fr.ocr.exception.PrjExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Synchronized;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

@Api(value = "APIs de gestion des Prets.")
@RestController
public class PretController {

    final PretService pretService;
    final UserService userService;
    final OuvrageService ouvrageService;
    final PrjExceptionHandler prjExceptionHandler;

    public PretController(PretService pretService, UserService userService, OuvrageService ouvrageService, PrjExceptionHandler prjExceptionHandler) {
        this.pretService = pretService;
        this.userService = userService;
        this.ouvrageService = ouvrageService;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts d'un user grâce à son nom")
    @GetMapping(value="/CriteriaListePrets/{userName}",  produces= MediaType.APPLICATION_JSON_VALUE)
    public  List<PretDtoWeb> getPretByNomUsagerCriteria(@PathVariable String userName) {
        return pretService.getPretsByUsagerNameWithCriteria(userService.getUserByNom(userName).getIdUser());
    }

    @ApiOperation(value = "Prolonge le Pret d'un user")
    @PutMapping(value = "/ProlongerPret")
    @Synchronized
    public ResponseEntity<Map<String, Integer>> prolongerPret(@RequestBody InfosRecherchePret infosRecherchePret) {

        pretService.setProlongationPret(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());

        return     infosRecherchePret.formeReponseEntity( );
    }

    @ApiOperation(value = "Creation d'un Pret : un Ouvrage/un User")
    @PostMapping(value = "/CreerPret")
    @Synchronized
    public ResponseEntity<Map<String, Integer>> CreerPret(@RequestBody InfosRecherchePret infosRecherchePret) {
        Optional<Pret> optionalPret = pretService.isPretExiste(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());
        if (optionalPret.isPresent()) {
            prjExceptionHandler.throwPretConflict("Pret deja existant");
        }
        else {
            ouvrageService.setQuantiteByIdOuvrage(infosRecherchePret.getIdOuvrage(),-1);
            pretService.addPret(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());
        }
        return     infosRecherchePret.formeReponseEntity( );
    }

    @ApiOperation(value = "Restitution d'un Pret : un Ouvrage/un User")
    @PostMapping (value = "/RestituerPret")
    @Synchronized
    public ResponseEntity<Map<String, Integer>> RestituerPret(@RequestBody InfosRecherchePret infosRecherchePret) {
        Optional<Pret> optionalPret = pretService.isPretExiste(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());
        if (optionalPret.isEmpty()) {
            prjExceptionHandler.throwPretNotAcceptable("Pret n'existe pas");
        }
        else {
            ouvrageService.setQuantiteByIdOuvrage(infosRecherchePret.getIdOuvrage(),1);
            pretService.deletePret(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());
        }
        return     infosRecherchePret.formeReponseEntity( );
    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts hors-delai ")
    @GetMapping(value="/ListePretsHorsDelai",  produces= MediaType.APPLICATION_JSON_VALUE)
    public List<PretDtoBatch> getPretByIdUsagerHorsDelai(@RequestParam(value = "currentDate") String sDateCourante,
                                                         @RequestParam(value = "elapsedWeeks") Integer nbWeeks) throws ParseException {
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(sdf.parse(sDateCourante));
        calendar.add(Calendar.WEEK_OF_YEAR,nbWeeks * -1);
        return pretService.getPretByeDueDate(calendar.getTime());
    }

}
