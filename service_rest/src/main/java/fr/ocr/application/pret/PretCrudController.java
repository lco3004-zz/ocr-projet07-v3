/*
 *
 * Controlleur :
 * *
 * * Renvoie la liste des prets d'un usager dont le nom est passé en param. méthode GET
 * * Renvoie la liste des prets HORS-Delai selon date et periode (ex : date du jour et période = 4 semaines)
 * * Prolonge un pret d'un usager. méthode PUT
 * * Crée un pret pour un idOuvrage et un idUsager passé en params
 * * Restitue un pret pour un idOuvrage et un idUsager passé en params
 */
package fr.ocr.application.pret;

import fr.ocr.application.ouvrage.OuvrageCrudService;
import fr.ocr.application.user.UserCrudService;
import fr.ocr.exception.PrjExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Synchronized;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Api(value = "APIs de gestion des Prets.")
@RestController
public class PretCrudController {

    final PretCrudService pretCrudService;
    final UserCrudService userCrudService;
    final OuvrageCrudService ouvrageCrudService;
    final PrjExceptionHandler prjExceptionHandler;

    public PretCrudController(PretCrudService pretCrudService, UserCrudService userCrudService, OuvrageCrudService ouvrageCrudService, PrjExceptionHandler prjExceptionHandler) {
        this.pretCrudService = pretCrudService;
        this.userCrudService = userCrudService;
        this.ouvrageCrudService = ouvrageCrudService;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @ApiOperation(value = "Récupère les prêts d'un user grâce à son nom")
    @GetMapping(value="/CriteriaListePrets/{userName}",  produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public  List<PretCrudDtoWeb> getPretByNomUsagerCriteria(@PathVariable String userName) throws  RuntimeException{
        return pretCrudService.getPretsByUsagerNameWithCriteria(userCrudService.getUserByNom(userName).getIdUser());
    }

    @ApiOperation(value = "Prolonge le Pret d'un user")
    @PutMapping(value = "/ProlongerPret",  produces= MediaType.APPLICATION_JSON_VALUE)
    @Synchronized
    public   PretCrudDtoWeb prolongerPret(@RequestBody InfosRecherchePret infosRecherchePret) throws  RuntimeException{

        PretCrudDtoWeb pretCrudDtoWeb = null;
        try {

            Pret pret = pretCrudService.setProlongationPret(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());

            pretCrudDtoWeb = new PretCrudDtoWeb(pret.getOuvrageIdouvrage(), pret.getUserIduser(),
                    pret.getPretprolonge(), pret.getDateEmprunt(),
                    pret.getAuteurOuvrage(), pret.getTitreOuvrage());

        }catch (Exception e) {
                prjExceptionHandler.throwProlongationAlreadyReported("ce prêt a déja été renouvelé ou est hors-délai");
        }

        return pretCrudDtoWeb;
    }

    @ApiOperation(value = "Creation d'un Pret : un Ouvrage/un User")
    @PostMapping(value = "/CreerPret")
    @Synchronized
    public ResponseEntity<Map<String, Integer>> CreerPret(@RequestBody InfosRecherchePret infosRecherchePret) {
        Optional<Pret> optionalPret = pretCrudService.isPretExiste(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());
        if (optionalPret.isPresent()) {
            prjExceptionHandler.throwPretConflict("Pret deja existant");
        }
        else {
            ouvrageCrudService.setQuantiteByIdOuvrage(infosRecherchePret.getIdOuvrage(),-1);
            pretCrudService.addPret(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());
        }
        return     infosRecherchePret.formeReponseEntity( );
    }

    @ApiOperation(value = "Restitution d'un Pret : un Ouvrage/un User")
    @PostMapping (value = "/RestituerPret")
    @Synchronized
    public ResponseEntity<Map<String, Integer>> RestituerPret(@RequestBody InfosRecherchePret infosRecherchePret) {
        Optional<Pret> optionalPret = pretCrudService.isPretExiste(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());
        if (optionalPret.isEmpty()) {
            prjExceptionHandler.throwPretNotAcceptable("Pret n'existe pas");
        }
        else {
            ouvrageCrudService.setQuantiteByIdOuvrage(infosRecherchePret.getIdOuvrage(),1);
            pretCrudService.deletePret(infosRecherchePret.getIdOuvrage(),infosRecherchePret.getIdUser());
        }
        return     infosRecherchePret.formeReponseEntity( );
    }

    @ApiOperation(value = "Récupère les prêts hors-delai ")
    @GetMapping(value="/ListePretsHorsDelai",  produces= MediaType.APPLICATION_JSON_VALUE)
    @Synchronized
    public List<PretCrudDtoBatch> getPretByIdUsagerHorsDelai(@RequestParam(value = "currentDate") String sDateCourante,
                                                             @RequestParam(value = "elapsedWeeks") Integer nbWeeks) throws ParseException {
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(sdf.parse(sDateCourante));
        calendar.add(Calendar.WEEK_OF_YEAR,nbWeeks * -1);
        return pretCrudService.getPretByeDueDate(calendar.getTime());
    }

}
