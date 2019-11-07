package fr.ocr.controller;


import fr.ocr.domain.PretDtoWeb;
import fr.ocr.service.PretService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Synchronized;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Value
class InfosRecherchePret {
    Integer idUsager;
    Integer idOuvrage;
}

@Api(value = "APIs de gestion des Prets.")
@RestController
public class PretController {

    final PretService pretService;


    public PretController(PretService pretService) {
        this.pretService = pretService;
    }

    @ApiOperation(value = "Api Criteria : Récupère les prêts d'un usager grâce à son nom")
    @GetMapping(value="/CriteriaListePrets/{nomUsager}",  produces= MediaType.APPLICATION_JSON_VALUE)
    public  List<PretDtoWeb> getPretByNomUsagerCriteria(@PathVariable String nomUsager) throws IOException, InterruptedException {
        return pretService.getPretsByUsagerNameWithCriteria(nomUsager);
    }

    @ApiOperation(value = "Prolonge le Pret d'un usager")
    @PutMapping(value = "/ProlongerPret")
    public ResponseEntity<Void> prolongerPret(@RequestBody Map<String,String> criterionList) {
        HttpStatus httpStatus = pretService.setProlongationPret(criterionList);
        URI location = ServletUriComponentsBuilder.fromUri(URI.create("/ProlongerPret")).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

}
