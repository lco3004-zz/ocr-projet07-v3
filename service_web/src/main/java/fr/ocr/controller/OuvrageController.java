package fr.ocr.controller;

import fr.ocr.domain.OuvrageDtoWeb;
import fr.ocr.service.OuvrageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(value = "APIs de gestion des Ouvrages.")
@RestController
public class OuvrageController {

    final OuvrageService ouvrageService;

    public OuvrageController(OuvrageService ouvrageService) {
        this.ouvrageService = ouvrageService;
    }

    @ApiOperation(value = "Recherche d'ouvrage par titre ou par auteur")
    @PostMapping(value="/LookForOuvrage")
    public List<OuvrageDtoWeb> getOuvrageByQuery(@RequestBody(required = false) Map<String,String> criterionList) throws IOException, InterruptedException {
        return ouvrageService.getOuvrageByQuerie(criterionList);
    }

}
