package fr.ocr.domain.pret;

import fr.ocr.domain.ouvrage.Ouvrage;
import fr.ocr.domain.ouvrage.OuvrageService;
import fr.ocr.domain.usager.Usager;
import fr.ocr.domain.usager.UsagerService;
import fr.ocr.utility.exception.PretDejaExistantException;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Value
class InfosRecherchePret {
    String nomUsager;
    Integer idOuvrage;
}

@Api(value = "APIs de gestion des Prets.")
@RestController
public class PretController {

    final PretService pretService;
    final UsagerService usagerService;
    final OuvrageService ouvrageService;

    public PretController(PretService pretService, UsagerService usagerService,  OuvrageService ouvrageService) {
        this.pretService = pretService;
        this.usagerService = usagerService;
        this.ouvrageService = ouvrageService;
    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts d'un usager grâce à son nom")
    @ResponseBody
    @GetMapping(value="/CriteriaListePrets/{nomUsager}",  produces= MediaType.APPLICATION_JSON_VALUE)
    public  List<PretDtoWeb> getPretByNomUsagerCriteria(@PathVariable String nomUsager) {
        Usager usager= usagerService.getUsagerByNom(nomUsager);
        List<PretDtoWeb> pretDtoWebs = pretService.getPretsByUsagerNameWithCriteria(usager);
        return  pretDtoWebs ;
    }

    @ApiOperation(value = "Prolonge le Pret d'un usager")
    @PutMapping(value = "/ProlongerPret")
    @Synchronized
    public ResponseEntity<Void> prolongerPret(@RequestBody(required = true) InfosRecherchePret infosRecherchePret) {
        Ouvrage ouvrage = ouvrageService.getOuvrageById(infosRecherchePret.getIdOuvrage());
        Usager usager = usagerService.getUsagerByNom(infosRecherchePret.getNomUsager());
        pretService.setProlongationPret(ouvrage.getIdouvrage(),usager.getIdusager());

        URI location = ServletUriComponentsBuilder.fromUri(URI.create("/ProlongerPret")).buildAndExpand().toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Creation d'un Pret : un Ouvrage/un Usager")
    @PostMapping(value = "/CreerPret")
    @Synchronized
    public ResponseEntity<Void> CreerPret(@RequestBody(required = true) InfosRecherchePret infosRecherchePret) {

        Ouvrage ouvrage = ouvrageService.getOuvrageById(infosRecherchePret.getIdOuvrage());
        Usager usager = usagerService.getUsagerByNom(infosRecherchePret.getNomUsager());
        Optional<Pret> optionalPret = pretService.isPretExiste(ouvrage.getIdouvrage(),usager.getIdusager());
        if (optionalPret.isEmpty()) {
            throw new PretDejaExistantException("Pret deja existant");
        }
        else {
            ouvrageService.setQuantiteByIdOuvrage(ouvrage.getIdouvrage(),-1);
            pretService.addPret(ouvrage,usager);
        }

        URI location = ServletUriComponentsBuilder.fromUri(URI.create("/CreerPret")).buildAndExpand().toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Restitution d'un Pret : un Ouvrage/un Usager")
    @PostMapping (value = "/RestituerPret")
    @Synchronized
    public ResponseEntity<Void> RestituerPret(@RequestBody(required = true) InfosRecherchePret infosRecherchePret) {

        Ouvrage ouvrage = ouvrageService.getOuvrageById(infosRecherchePret.getIdOuvrage());
        Usager usager = usagerService.getUsagerByNom(infosRecherchePret.getNomUsager());

        Optional<Pret> optionalPret = pretService.isPretExiste(ouvrage.getIdouvrage(),usager.getIdusager());

        if (optionalPret.isEmpty()) {
            throw new PretDejaExistantException("Pret n'existe pas");
        }
        else {
            ouvrageService.setQuantiteByIdOuvrage(ouvrage.getIdouvrage(),1);
            pretService.deletePret(ouvrage,usager);
        }

        URI location = ServletUriComponentsBuilder.fromUri(URI.create("/RestituerPret")).buildAndExpand().toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts hors-delai ")
    @ResponseBody
    @GetMapping(value="/ListePretsHorsDelai/",  produces= MediaType.APPLICATION_JSON_VALUE)
    public List<PretDtoBatch> getPretByIdUsagerHorsDelai(@RequestParam(value = "currentDate") String sDateCourante,
                                                         @RequestParam(value = "elapsedWeeks") Integer nbWeeks) throws ParseException {

        GregorianCalendar calendar = new GregorianCalendar();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(sdf.parse(sDateCourante));
        calendar.add(Calendar.WEEK_OF_YEAR,nbWeeks * -1);

        List<PretDtoBatch> pretDtoBatchList = pretService.getPretByeDueDate(calendar.getTime());

        return pretDtoBatchList;
    }

}
